package no.nav.eux.saksbehandler.webapp.controller

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import no.nav.eux.saksbehandler.service.SaksbehandlerService
import no.nav.eux.saksbehandler.webapp.model.SaksbehandlerGetApiModel
import no.nav.eux.saksbehandler.webapp.model.SaksbehandlerPutApiModel
import no.nav.security.token.support.core.api.Protected
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Protected
@RestController
@Tag(name = "Saksbehandler", description = "API for å hente og oppdatere saksbehandlerinformasjon")
class SaksbehandlerController(
    val service: SaksbehandlerService
) {

    val log = logger {}

    @Operation(
        summary = "Hent saksbehandler",
        description = "Henter informasjon om en saksbehandler basert på NAV ident"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Saksbehandler funnet",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SaksbehandlerGetApiModel::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Saksbehandler ikke funnet",
                content = [Content()]
            )
        ]
    )
    @GetMapping(
        value = ["/api/v1/saksbehandlere/{navIdent}"],
        produces = ["application/json"]
    )
    fun getSaksbehandler(
        @Parameter(description = "NAV ident til saksbehandler", required = true, example = "Z999999")
        @PathVariable("navIdent")
        navIdent: String
    ): ResponseEntity<SaksbehandlerGetApiModel> {
        val saksbehandler = service.get(navIdent)
        return if (saksbehandler == null) {
            log.info { "Saksbehandler ikke funnet: $navIdent" }
            ResponseEntity(NOT_FOUND)
        } else {
            log.info { "Hentet saksbehandler $navIdent" }
            ResponseEntity(saksbehandler.toSaksbehandlerGetApiModel(), OK)
        }
    }

    @Operation(
        summary = "Lagre saksbehandler",
        description = "Lagrer eller oppdaterer informasjon om en saksbehandler"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Saksbehandler lagret",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Ugyldig forespørsel",
                content = [Content()]
            )
        ]
    )
    @PutMapping(
        value = ["/api/v1/saksbehandlere/{navIdent}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun putSaksbehandlere(
        @Parameter(description = "NAV ident til saksbehandler", required = true, example = "Z999999")
        @PathVariable("navIdent")
        navIdent: String,
        @Parameter(description = "Saksbehandlerinformasjon som skal lagres", required = true)
        @Valid
        @RequestBody
        saksbehandler: SaksbehandlerPutApiModel
    ): ResponseEntity<Void> {
        service.save(saksbehandler.toEntity(navIdent))
        log.info { "Saksbehandler lagret: $navIdent" }
        return ResponseEntity(NO_CONTENT)
    }

    @Operation(
        summary = "Fjerner saksbehandler",
        description = "Fjerner informasjon om en saksbehandler"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Saksbehandler fjernet",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Ugyldig forespørsel",
                content = [Content()]
            )
        ]
    )
    @DeleteMapping(
        value = ["/api/v1/saksbehandlere/{navIdent}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun deleteSaksbehandlere(
        @Parameter(description = "NAV ident til saksbehandler", required = true, example = "Z999999")
        @PathVariable("navIdent")
        navIdent: String
    ): ResponseEntity<Void> {
        service.delete(navIdent)
        log.info { "Saksbehandler fjernet: $navIdent" }
        return ResponseEntity(NO_CONTENT)
    }
}
