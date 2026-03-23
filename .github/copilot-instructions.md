# Copilot Instructions — eux-saksbehandler

## Architecture

Kotlin Spring Boot 4 REST API deployed on [NAIS](https://doc.nais.io/) (Kubernetes on GCP). Stores NAV case worker ("saksbehandler") preferences in PostgreSQL. Authenticated via Azure AD (JWT).

### Package structure

```
no.nav.eux.saksbehandler
├── Application.kt              # Entry point, bean config (DataSource, OpenAPI, MDC filter)
├── config/                     # @ConfigurationProperties classes
├── model/                      # JPA entities (data classes, immutable vals)
├── persistence/                # Spring Data JPA repositories
├── service/                    # Business logic
└── webapp/
    ├── controller/             # REST controllers + mapper extension functions
    └── model/                  # API models (request/response DTOs)
```

### Request flow

Controller (`@Protected`, JWT-validated) → Service → Repository → PostgreSQL

### Database

PostgreSQL with Flyway migrations in `src/main/resources/db/migration/`. Naming: `V<number>__<description>.sql`. Single migration exists (`V1__saksbehandler.sql`).

## Key Conventions

### Kotlin style

- **JPA entities**: `data class` with all `val` properties, in `model/` package
- **API models**: Separate `Get` and `Put` DTOs in `webapp/model/`. Annotated with `@Schema` for OpenAPI docs
- **DTO ↔ entity mapping**: Kotlin extension functions in a `*Mapper.kt` file in the controller package — no mapping libraries
- **Validation**: Jakarta Bean Validation annotations with `@field:` target on request DTOs. Error messages in Norwegian
- **Logging**: `io.github.oshai:kotlin-logging-jvm` (`val log = logger {}`)
- **Properties**: Constructor injection everywhere, `val` for injected dependencies

### REST API conventions

- All endpoints under `/api/v1/`
- Controllers annotated with `@Protected` (NAV token-validation-spring)
- Full OpenAPI/Swagger annotations (`@Operation`, `@ApiResponse`, `@Tag`, `@Schema`)
- PUT returns `204 No Content`, GET returns entity or `404`

### Testing

- **Integration tests only** — full Spring Boot context with `@SpringBootTest(webEnvironment = RANDOM_PORT)`
- Base class `AbstractSaksbehandlerApiImplTest` provides:
  - Mock OAuth2 server (`@EnableMockOAuth2Server`)
  - `TestRestTemplate` for HTTP calls
  - `@BeforeEach` database cleanup via `JdbcTestUtils.deleteFromTables()`
  - Helper methods for each API endpoint (`putSaksbehandler()`, `getSaksbehandler()`, etc.)
  - Infix assertion `statusCodeShouldBe` for readable status checks
- **Assertions**: Kotest (`shouldBe`, `shouldEqualJson`)
- **Test names**: Norwegian descriptive sentences using backtick syntax
- **Database**: Testcontainers PostgreSQL (started automatically, no manual setup)

### Deployment

NAIS manifests in `.nais/`. Three environments: q1 (dev), q2 (dev), prod. The `#q-test` commit message tag skips prod deployment.
