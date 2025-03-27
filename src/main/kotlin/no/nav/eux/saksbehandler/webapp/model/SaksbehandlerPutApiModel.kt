package no.nav.eux.saksbehandler.webapp.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Informasjon om Nav saksbehandler")
data class SaksbehandlerPutApiModel(
    @Schema(
        description = """
        Brukeren sin foretrukne enhet. Enhet legges ved handlinger 
        som opprettelse av oppgave og journalføring", example = "2950
        """
    )
    val favorittEnhetNr: String,
)
