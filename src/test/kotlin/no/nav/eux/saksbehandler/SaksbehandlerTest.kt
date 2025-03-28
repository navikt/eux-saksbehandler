package no.nav.eux.saksbehandler

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.GET

class SaksbehandlerTest : AbstractSaksbehandlerApiImplTest() {

    @Test
    fun `GET rinasaker - foresporsel, finn med id - 200`() {
        restTemplate.exchange(
            "/api/v1/saksbehandlere/1",
            HttpMethod.PUT,
            SaksbehandlerPutApiModelTest(favorittEnhetNr = "2950").httpEntity,
            Void::class.java
        )
        val entity = restTemplate.exchange(
            "/api/v1/saksbehandlere/1",
            GET,
            httpEntity(),
            String::class.java
        )
        entity.statusCode.value() shouldBe 200
        val saksbehandler = entity.body!!
        saksbehandler shouldEqualJson """
            {
              "navIdent": "1",
              "favorittEnhetNr": "2950"
            }
        """
        restTemplate.exchange(
            "/api/v1/saksbehandlere/1",
            HttpMethod.PUT,
            SaksbehandlerPutApiModelTest(favorittEnhetNr = "2951").httpEntity,
            Void::class.java
        )
        val entityUpdate = restTemplate.exchange(
            "/api/v1/saksbehandlere/1",
            GET,
            httpEntity(),
            String::class.java
        )
        entityUpdate.statusCode.value() shouldBe 200
        val saksbehandlerUpdate = entityUpdate.body!!
        saksbehandlerUpdate shouldEqualJson """
            {
              "navIdent": "1",
              "favorittEnhetNr": "2951"
            }
        """
    }

    @Test
    fun `GET rinasaker - foresporsel, ikke funnet - 404`() {
        val entity = restTemplate.exchange<Void>(
            url = "/api/v1/saksbehandlere/finnesIkke",
            method = GET,
            requestEntity = httpEntity()
        )
        entity.statusCode.value() shouldBe 404
    }
}

data class SaksbehandlerPutApiModelTest(
    val favorittEnhetNr: String,
)
