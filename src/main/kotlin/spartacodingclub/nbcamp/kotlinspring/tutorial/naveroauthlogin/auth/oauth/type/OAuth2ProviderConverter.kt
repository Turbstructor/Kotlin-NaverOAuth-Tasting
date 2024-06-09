package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type

import org.springframework.core.convert.converter.Converter

class OAuth2ProviderConverter: Converter<String, OAuth2Provider> {

    override fun convert(source: String): OAuth2Provider? {
       return runCatching { OAuth2Provider.valueOf(source.uppercase()) }
           .getOrElse { throw IllegalArgumentException() }
    }
}