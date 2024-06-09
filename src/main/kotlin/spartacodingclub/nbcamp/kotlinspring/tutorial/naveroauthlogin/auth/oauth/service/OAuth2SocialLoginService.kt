package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.service

import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.client.OAuth2Client
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.dto.SocialLoginInfo
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2Provider

@Service
class OAuth2SocialLoginService(
    private val clients: List<OAuth2Client>
) {

    fun redirectToSocialLogin(provider: OAuth2Provider): String =
        this.selectClient(provider).socialLoginUrl()

    fun getSocialLoginInfo(provider: OAuth2Provider, stateToken: String, authorizationCode: String): SocialLoginInfo =
        this.selectClient(provider).let { client ->
            client.getAccessToken(stateToken,  authorizationCode).let { accessToken ->
                client.getUserInfo(accessToken)
            }
        }

    private fun selectClient(provider: OAuth2Provider): OAuth2Client =
        clients.find { it.supports(provider) } ?: throw IllegalArgumentException("OAuth2 Provider not found")
}