package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2ProviderConverter

@Configuration
class AuthConfig : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
       registry.addConverter(OAuth2ProviderConverter())
    }
}