package no.nav.eux.saksbehandler

import no.nav.eux.logging.RequestIdMdcFilter

import no.nav.security.token.support.spring.api.EnableJwtTokenValidation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableJwtTokenValidation(
    ignore = [
        "org.springframework",
        "org.springdoc"
    ]
)
@SpringBootApplication
@EnableConfigurationProperties(
)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
class ApplicationConfig(
) {

    @Bean
    fun requestIdMdcFilter() = RequestIdMdcFilter()

}
