package no.nav.eux.saksbehandler

import no.nav.eux.saksbehandler.common.httpEntity
import no.nav.eux.saksbehandler.common.voidHttpEntity
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.token.support.spring.test.EnableMockOAuth2Server
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.jdbc.JdbcTestUtils

@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@EnableMockOAuth2Server
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

    fun httpEntity() = voidHttpEntity(mockOAuth2Server)

    val <T> T.httpEntity: HttpEntity<T>
        get() = httpEntity(mockOAuth2Server)

}
