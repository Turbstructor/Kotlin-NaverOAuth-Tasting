package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.client.naver.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class NaverUserInfoQueryResponse(
    val resultcode: String,
    val message: String,
    val response: NaverUserInfoResponse
)
