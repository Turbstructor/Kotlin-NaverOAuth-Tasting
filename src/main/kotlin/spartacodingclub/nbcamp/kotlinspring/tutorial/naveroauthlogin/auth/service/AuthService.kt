package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.service

import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.dto.response.SignInResponse
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.service.OAuth2SocialLoginService
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2Provider
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.member.service.MemberService

@Service
class AuthService(
    private val memberService: MemberService,
    private val oAuth2SocialLoginService: OAuth2SocialLoginService
) {

    fun signIn(provider: OAuth2Provider, stateToken: String, authorizationCode: String): SignInResponse =
        memberService.signIn(oAuth2SocialLoginService.getSocialLoginInfo(provider, stateToken, authorizationCode))
}