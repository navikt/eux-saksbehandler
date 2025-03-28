package no.nav.eux.saksbehandler.persistence

import no.nav.eux.saksbehandler.model.Saksbehandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SaksbehandlerRepository : JpaRepository<Saksbehandler, String>
