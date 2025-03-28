package no.nav.eux.saksbehandler.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Saksbehandler(
    @Id
    val navIdent: String,
    val favorittEnhetNr: String,
)
