package no.nav.eux.saksbehandler.webapp.controller

import no.nav.eux.saksbehandler.model.Saksbehandler
import no.nav.eux.saksbehandler.webapp.model.SaksbehandlerGetApiModel
import no.nav.eux.saksbehandler.webapp.model.SaksbehandlerPutApiModel

fun SaksbehandlerPutApiModel.toEntity(navIdent: String) =
    Saksbehandler(
        navIdent = navIdent,
        favorittEnhetNr = favorittEnhetNr,
    )

fun Saksbehandler.toSaksbehandlerGetApiModel() =
    SaksbehandlerGetApiModel(
        navIdent = navIdent,
        favorittEnhetNr = favorittEnhetNr,
    )
