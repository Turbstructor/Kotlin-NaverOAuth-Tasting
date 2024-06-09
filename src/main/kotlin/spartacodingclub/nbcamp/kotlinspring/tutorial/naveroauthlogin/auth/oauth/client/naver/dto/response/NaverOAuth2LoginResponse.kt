package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.client.naver.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class NaverOAuth2LoginResponse(
    val accessToken: String = "",
    val refreshToken: String = "",
    val tokenType: String = "",
    val expiresIn: Int = 0
)