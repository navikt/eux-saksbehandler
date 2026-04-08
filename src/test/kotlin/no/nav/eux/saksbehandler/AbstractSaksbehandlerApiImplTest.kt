package no.nav.eux.saksbehandler

import no.nav.eux.saksbehandler.common.token
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.token.support.spring.test.EnableMockOAuth2Server
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.jdbc.JdbcTestUtils
import org.springframework.test.web.servlet.client.RestTestClient

@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@EnableMockOAuth2Server
@AutoConfigureRestTestClient
abstract class AbstractSaksbehandlerApiImplTest {

    @Autowired
    lateinit var mockOAuth2Server: MockOAuth2Server

    @Autowired
    lateinit var restTestClient: RestTestClient

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun initialiseRestAssuredMockMvcWebApplicationContext() {
        JdbcTestUtils.deleteFromTables(
            jdbcTemplate,
            "saksbehandler",
        )
    }

    fun putSaksbehandler(navIdent: String, favorittEnhetNr: String) =
        restTestClient.put().uri("/api/v1/saksbehandlere/$navIdent")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(SaksbehandlerPutApiModelTest(favorittEnhetNr))
            .exchange()

    fun getSaksbehandler(navIdent: String) =
        restTestClient.get().uri("/api/v1/saksbehandlere/$navIdent")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()

    fun deleteSaksbehandler(navIdent: String) =
        restTestClient.delete().uri("/api/v1/saksbehandlere/$navIdent")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
}

data class SaksbehandlerPutApiModelTest(
    val favorittEnhetNr: String,
)
