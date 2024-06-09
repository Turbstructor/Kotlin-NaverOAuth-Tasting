package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.member.entity

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.dto.SocialLoginInfo
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2Provider

@Entity
@Table(name = "member")
class Member (

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider?,

    @Column(nullable = true)
    val providerId: String?,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var nickname: String
) {

    constructor(request: SocialLoginInfo): this(
        provider = request.provider,
        providerId = request.id,
        email = request.email,
        nickname = request.nickname
    )

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}