package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.member.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2Provider
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.member.entity.Member

@Repository
interface MemberRepository : CrudRepository<Member, Long> {

    fun findByProviderAndProviderId(provider: OAuth2Provider, providerId: String): Member?
}