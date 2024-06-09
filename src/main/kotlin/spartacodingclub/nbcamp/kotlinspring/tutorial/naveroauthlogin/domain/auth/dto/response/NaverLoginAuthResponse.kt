package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.auth.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class NaverLoginAuthResponse(
    val accessToken: String = "",
    val refreshToken: String = "",
    val tokenType: String = "",
    val expiresIn: Int = 0
)
