package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.member.service

import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.dto.response.SignInResponse
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.dto.SocialLoginInfo
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.member.entity.Member
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.member.repository.MemberRepository

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun signIn(socialLoginInfo: SocialLoginInfo): SignInResponse =
        memberRepository.findByProviderAndProviderId(socialLoginInfo.provider, socialLoginInfo.id)?.toSignInResponse()
            ?: signUp(socialLoginInfo)

    private fun signUp(socialLoginInfo: SocialLoginInfo): SignInResponse =
        memberRepository.save(
            Member(
                provider = socialLoginInfo.provider,
                providerId = socialLoginInfo.id,
                email = socialLoginInfo.email,
                nickname = socialLoginInfo.nickname
            )
        ).toSignInResponse()
}