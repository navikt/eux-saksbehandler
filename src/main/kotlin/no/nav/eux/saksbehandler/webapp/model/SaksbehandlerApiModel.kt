package no.nav.eux.saksbehandler.webapp.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Informasjon om Nav saksbehandler")
data class SaksbehandlerApiModel(
    @Schema(description = "Nav ident", example = "h278060")
    val navIdent: String,
    @Schema(
        description = """
        Brukeren sin foretrukne enhet. Enhet legges ved handlinger 
        som opprettelse av oppgave og journalføring", example = "2950
        """
    )
    val favorittEnhetNr: String,
)
