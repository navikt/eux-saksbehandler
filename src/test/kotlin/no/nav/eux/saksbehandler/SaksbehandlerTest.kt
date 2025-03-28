package no.nav.eux.saksbehandler

import io.kotest.assertions.json.shouldEqualJson
import org.junit.jupiter.api.Test

class SaksbehandlerTest : AbstractSaksbehandlerApiImplTest() {

    @Test
    fun `GET rinasaker - foresporsel, finn med id - 200`() {
        putSaksbehandler("1", "2950")
        val getResponse = getSaksbehandler("1")
        getResponse statusCodeShouldBe 200
        val responseBody = getResponse.body!!
        responseBody shouldEqualJson """
            {
              "navIdent": "1",
              "favorittEnhetNr": "2950"
            }
        """
        putSaksbehandler("1", "2951")
        val updateResponse = getSaksbehandler("1")
        updateResponse statusCodeShouldBe 200
        val updatedResponseBody = updateResponse.body!!
        updatedResponseBody shouldEqualJson """
            {
              "navIdent": "1",
              "favorittEnhetNr": "2951"
            }
        """
    }

    @Test
    fun `GET rinasaker - foresporsel, ikke funnet - 404`() {
        val getResponse = getSaksbehandler("finnesIkke")
        getResponse statusCodeShouldBe 404
    }

    @Test
    fun `PUT saksbehandlere - ugyldig data - 400`() {
        val putResponse = putSaksbehandler("3", "123")
        putResponse statusCodeShouldBe 400
    }

    @Test
    fun `DELETE saksbehandlere - slett eksisterende - 204`() {
        putSaksbehandler("4", "2950")
        val getResponseBefore = getSaksbehandler("4")
        getResponseBefore statusCodeShouldBe 200
        val deleteResponse = deleteSaksbehandler("4")
        deleteResponse statusCodeShouldBe 204
        val getResponseAfter = getSaksbehandler("4")
        getResponseAfter statusCodeShouldBe 404
    }

    @Test
    fun `DELETE saksbehandlere - finnes ikke - 404`() {
        val deleteResponse = deleteSaksbehandler("finnesIkke")
        deleteResponse statusCodeShouldBe 404
    }

    @Test
    fun `PUT saksbehandlere - tom favorittEnhetNr - 400`() {
        val putResponse = putSaksbehandler("6", "")
        putResponse statusCodeShouldBe 400
    }
}