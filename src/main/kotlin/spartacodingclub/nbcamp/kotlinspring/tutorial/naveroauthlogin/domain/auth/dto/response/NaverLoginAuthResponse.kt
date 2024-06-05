package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.auth.dto.response

data class NaverLoginAuthResponse(
    val code: String,
    val state: String,
    val error: String,
    val errorDescription: String
)
