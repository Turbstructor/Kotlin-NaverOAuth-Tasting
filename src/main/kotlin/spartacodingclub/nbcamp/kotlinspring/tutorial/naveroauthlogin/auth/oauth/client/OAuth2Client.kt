package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.client

import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.dto.SocialLoginInfo
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2Provider

interface OAuth2Client {

    fun socialLoginUrl(): String
    fun getAccessToken(stateToken: String, authorizationCode: String): String
    fun getUserInfo(accessToken: String): SocialLoginInfo

    fun supports(provider: OAuth2Provider): Boolean
}