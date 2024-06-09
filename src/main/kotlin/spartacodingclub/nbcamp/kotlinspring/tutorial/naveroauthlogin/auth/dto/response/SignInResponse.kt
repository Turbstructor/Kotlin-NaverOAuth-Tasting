package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.dto.response

data class SignInResponse(
    val id: Long?,
    val nickname: String,
    val accessToken: String
)
