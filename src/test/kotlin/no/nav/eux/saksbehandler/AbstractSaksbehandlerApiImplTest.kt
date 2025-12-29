package no.nav.eux.saksbehandler

import io.kotest.matchers.shouldBe
import no.nav.eux.saksbehandler.common.httpEntity
import no.nav.eux.saksbehandler.common.voidHttpEntity
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.token.support.spring.test.EnableMockOAuth2Server
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.TestRestTemplate
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.jdbc.JdbcTestUtils

@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@EnableMockOAuth2Server
@AutoConfigureTestRestTemplate
abstract class AbstractSaksbehandlerApiImplTest {

    @Autowired
    lateinit var mockOAuth2Server: MockOAuth2Server

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun initialiseRestAssuredMockMvcWebApplicationContext() {
        JdbcTestUtils.deleteFromTables(
            jdbcTemplate,
            "saksbehandler",
        )
    }

    val <T : Any> T.httpEntity: HttpEntity<T>
        get() = httpEntity(mockOAuth2Server)

    fun httpEntity() = voidHttpEntity(mockOAuth2Server)

    infix fun ResponseEntity<*>.statusCodeShouldBe(expectedStatusCode: Int) {
        this.statusCode.value() shouldBe expectedStatusCode
    }

    fun putSaksbehandler(navIdent: String, favorittEnhetNr: String): ResponseEntity<Void> =
        restTemplate.exchange(
            "/api/v1/saksbehandlere/$navIdent",
            HttpMethod.PUT,
            SaksbehandlerPutApiModelTest(favorittEnhetNr).httpEntity,
            Void::class.java
        )

    fun getSaksbehandler(navIdent: String): ResponseEntity<String> =
        restTemplate.exchange(
            "/api/v1/saksbehandlere/$navIdent",
            HttpMethod.GET,
            httpEntity(),
            String::class.java
        )

    fun deleteSaksbehandler(navIdent: String): ResponseEntity<Void> =
        restTemplate.exchange(
            "/api/v1/saksbehandlere/$navIdent",
            HttpMethod.DELETE,
            httpEntity(),
            Void::class.java
        )
}

data class SaksbehandlerPutApiModelTest(
    val favorittEnhetNr: String,
)
