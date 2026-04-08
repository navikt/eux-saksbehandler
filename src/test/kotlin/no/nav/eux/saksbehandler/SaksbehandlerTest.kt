package no.nav.eux.saksbehandler

import io.kotest.assertions.json.shouldEqualJson
import org.junit.jupiter.api.Test

class SaksbehandlerTest : AbstractSaksbehandlerApiImplTest() {

    @Test
    fun `GET rinasaker - foresporsel, finn med id - 200`() {
        putSaksbehandler("1", "2950")
        val responseBody = getSaksbehandler("1")
            .expectStatus().isEqualTo(200)
            .expectBody(String::class.java)
            .returnResult()
            .responseBody!!
        responseBody shouldEqualJson """
            {
              "navIdent": "1",
              "favorittEnhetNr": "2950"
            }
        """
        putSaksbehandler("1", "2951")
        val updatedResponseBody = getSaksbehandler("1")
            .expectStatus().isEqualTo(200)
            .expectBody(String::class.java)
            .returnResult()
            .responseBody!!
        updatedResponseBody shouldEqualJson """
            {
              "navIdent": "1",
              "favorittEnhetNr": "2951"
            }
        """
    }

    @Test
    fun `GET rinasaker - foresporsel, ikke funnet - 404`() {
        getSaksbehandler("finnesIkke")
            .expectStatus().isEqualTo(404)
    }

    @Test
    fun `PUT saksbehandlere - ugyldig data - 400`() {
        putSaksbehandler("3", "123")
            .expectStatus().isEqualTo(400)
    }

    @Test
    fun `DELETE saksbehandlere - slett eksisterende - 204`() {
        putSaksbehandler("4", "2950")
        getSaksbehandler("4")
            .expectStatus().isEqualTo(200)
        deleteSaksbehandler("4")
            .expectStatus().isEqualTo(204)
        getSaksbehandler("4")
            .expectStatus().isEqualTo(404)
    }

    @Test
    fun `DELETE saksbehandlere - finnes ikke - 404`() {
        deleteSaksbehandler("finnesIkke")
            .expectStatus().isEqualTo(404)
    }

    @Test
    fun `PUT saksbehandlere - tom favorittEnhetNr - 400`() {
        putSaksbehandler("6", "")
            .expectStatus().isEqualTo(400)
    }
}