package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.service

import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.service.OAuth2SocialLoginService
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2Provider

@Service
class AuthService(
    private val oAuth2SocialLoginService: OAuth2SocialLoginService
) {

    fun signIn(provider: OAuth2Provider, stateToken: String, authorizationCode: String): String =
        oAuth2SocialLoginService.getSocialLoginInfo(provider, stateToken, authorizationCode).toString()
}