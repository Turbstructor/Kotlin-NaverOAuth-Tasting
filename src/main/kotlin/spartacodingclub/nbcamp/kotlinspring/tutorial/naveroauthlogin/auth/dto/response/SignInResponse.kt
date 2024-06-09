package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.dto.response

import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2Provider

data class SignInResponse(
    val id: Long?,
    val provider: OAuth2Provider?,
    val providerId: String?,
    val nickname: String
)
