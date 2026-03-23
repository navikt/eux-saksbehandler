package no.nav.eux.saksbehandler.service

import no.nav.eux.saksbehandler.model.Saksbehandler
import no.nav.eux.saksbehandler.persistence.SaksbehandlerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SaksbehandlerService(
    val repository: SaksbehandlerRepository,
) {

    fun get(navIdent: String): Saksbehandler? =
        repository.findByIdOrNull(navIdent)

    fun save(saksbehandler: Saksbehandler): Saksbehandler =
        repository.save(saksbehandler)

    fun delete(navIdent: String) {
        repository.deleteById(navIdent)
    }
}
