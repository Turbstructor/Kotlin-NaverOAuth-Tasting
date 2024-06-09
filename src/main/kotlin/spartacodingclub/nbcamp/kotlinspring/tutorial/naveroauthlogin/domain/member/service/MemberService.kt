package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.member.service

import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.dto.SocialLoginInfo
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.member.entity.Member
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.member.repository.MemberRepository

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun signIn(socialLoginInfo: SocialLoginInfo): Member =
        memberRepository.findByProviderAndProviderId(socialLoginInfo.provider, socialLoginInfo.id)
            ?: signUp(socialLoginInfo)

    private fun signUp(socialLoginInfo: SocialLoginInfo): Member =
        memberRepository.save(
            Member(
                provider = socialLoginInfo.provider,
                providerId = socialLoginInfo.id,
                email = socialLoginInfo.email,
                nickname = socialLoginInfo.nickname
            )
        )
}