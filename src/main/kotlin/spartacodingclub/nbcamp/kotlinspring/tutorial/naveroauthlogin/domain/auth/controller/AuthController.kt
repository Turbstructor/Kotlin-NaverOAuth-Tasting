package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.auth.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.auth.service.AuthService

@RequestMapping("/oauth2/naver")
@RestController
class AuthController(
    private val authService: AuthService
) {

    @GetMapping("/login")
    fun initSocialLogin(): RedirectView = authService.initSocialLogin()

    @GetMapping("/login/callback")
    fun callback(@RequestParam code: String, @RequestParam state: String): ResponseEntity<String> =
        ResponseEntity.ok().body(authService.checkValidity(code, state))

    @GetMapping
    fun bruh(): String = "Bruh"
}