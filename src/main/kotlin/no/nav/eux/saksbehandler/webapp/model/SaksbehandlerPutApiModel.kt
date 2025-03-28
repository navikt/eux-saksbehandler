package no.nav.eux.saksbehandler.webapp.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Schema(description = "Informasjon om Nav saksbehandler")
data class SaksbehandlerPutApiModel(
    @Schema(
        description = """
            Brukeren sin foretrukne enhet. Enhet legges ved handlinger 
            som opprettelse av oppgave og journalføring
        """,
        example = "2950"
    )
    @field:NotBlank(message = "Favoritt enhet nummer kan ikke være tom")
    @field:Size(min = 4, max = 4, message = "Favoritt enhet nummer må være nøyaktig 4 tegn")
    @field:Pattern(regexp = "^[0-9]{4}$", message = "Favoritt enhet nummer må bestå av 4 siffer")
    val favorittEnhetNr: String,
)
