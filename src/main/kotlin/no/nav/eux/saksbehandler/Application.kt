package no.nav.eux.saksbehandler

import com.zaxxer.hikari.HikariDataSource
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import no.nav.eux.logging.RequestIdMdcFilter
import no.nav.eux.saksbehandler.config.DataSourceProperties
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
    DataSourceProperties::class
)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
class ApplicationConfig(
    val dataSourceProperties: DataSourceProperties
) {

    @Bean
    fun hikariDataSource() = HikariDataSource(dataSourceProperties.hikari)

    @Bean
    fun requestIdMdcFilter() = RequestIdMdcFilter()

    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("EUX Saksbehandler API")
                    .description("API for håndtering av NAV saksbehandlerinformasjon")
                    .version("1.0.0")
            )

}
