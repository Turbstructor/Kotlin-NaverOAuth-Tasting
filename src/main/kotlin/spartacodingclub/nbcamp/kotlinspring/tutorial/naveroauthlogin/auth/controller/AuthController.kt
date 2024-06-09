package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.dto.response.SignInResponse
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.service.OAuth2SocialLoginService
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2Provider
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.service.AuthService

@RequestMapping("/oauth2/{provider}")
@RestController
class AuthController(
    private val authService: AuthService,
    private val oAuth2SocialLoginService: OAuth2SocialLoginService
) {

    @GetMapping("/login")
    fun initSocialLogin(@PathVariable provider: OAuth2Provider) = RedirectView(oAuth2SocialLoginService.redirectToSocialLogin(provider))

    @GetMapping("/login/callback")
    fun callback(@PathVariable provider: OAuth2Provider, @RequestParam code: String, @RequestParam state: String): ResponseEntity<SignInResponse> =
        ResponseEntity.status(HttpStatus.OK)
            .body(authService.signIn(provider, state, code))
    
}