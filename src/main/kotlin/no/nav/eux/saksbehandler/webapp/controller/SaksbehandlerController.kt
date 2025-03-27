package no.nav.eux.saksbehandler.webapp.controller

import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import no.nav.eux.saksbehandler.webapp.model.SaksbehandlerGetApiModel
import no.nav.eux.saksbehandler.webapp.model.SaksbehandlerPutApiModel
import no.nav.security.token.support.core.api.Protected
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Protected
@RestController
class SaksbehandlerController {

    @GetMapping(
        value = ["/api/v1/saksbehandlere/{navIdent}"],
        produces = ["application/json"]
    )
    fun getSaksbehandler(
        @Parameter(description = "", required = true)
        @PathVariable("navIdent")
        navIdent: String
    ): ResponseEntity<SaksbehandlerGetApiModel> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @PutMapping(
        value = ["/api/v1/saksbehandlere/{navIdent}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun putSaksbehandlere(
        @Parameter(description = "", required = true)
        @PathVariable("navIdent")
        navIdent: String,
        @Parameter(description = "", required = true)
        @Valid
        @RequestBody
        saksbehandler: SaksbehandlerPutApiModel
    ): ResponseEntity<Void> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
