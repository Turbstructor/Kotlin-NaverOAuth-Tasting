package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.dto

import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.client.naver.dto.response.NaverUserInfoQueryResponse
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2Provider

data class SocialLoginInfo(
    val provider: OAuth2Provider,
    val id: String,
    val nickname: String,
    val email: String
) {
    companion object
}

fun SocialLoginInfo.Companion.from(response: NaverUserInfoQueryResponse) = SocialLoginInfo(
    provider = OAuth2Provider.NAVER,
    id = response.response.id,
    nickname = response.response.nickname,
    email = response.response.email
)