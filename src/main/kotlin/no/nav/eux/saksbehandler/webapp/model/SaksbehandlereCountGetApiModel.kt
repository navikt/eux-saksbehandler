package no.nav.eux.saksbehandler.webapp.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Antall saksbehandlere")
data class SaksbehandlereCountGetApiModel(
    @Schema(description = "Antall saksbehandlere", example = "42")
    val antall: Long,
)

