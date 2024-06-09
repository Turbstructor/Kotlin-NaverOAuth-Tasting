package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.auth.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.view.RedirectView
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.domain.auth.dto.response.NaverLoginAuthResponse
import java.math.BigInteger
import java.net.URI
import java.net.URLEncoder
import java.security.SecureRandom
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Service
class AuthService(

    @Value("\${naver.application.client.id}")
    private val clientId: String,

    @Value("\${naver.application.client.secret}")
    private val clientSecret: String,

    @Value("\${naver.application.redirect_url}")
    private val redirectUrl: String,
) {

    private var stateTokens: MutableList<String> = mutableListOf()


    private fun generateState(): String = BigInteger(130, SecureRandom()).toString(32)

    fun initSocialLogin(): RedirectView {
        val stateToken = generateState()
        stateTokens.add(stateToken)

        val url = "https://nid.naver.com/oauth2.0/authorize?" +
                "client_id=${clientId}" +
                "&response_type=code" +
                "&redirect_uri=${URLEncoder.encode(redirectUrl, "UTF-8")}" +
                "&state=${stateToken}"

        return RedirectView(url)
    }

    private fun isStateValid(state: String): Boolean = (stateTokens.find{ it == state } == null)

    private fun retrieveAccessToken(code: String, state: String): String {
        val restClient: RestClient = RestClient.create()
        val requestUrl = "https://nid.naver.com/oauth2.0/token?" +
                "grant_type=authorization_code&" +
                "client_id=${clientId}&client_secret=${clientSecret}&" +
                "code=${code}&state=${state}"

        return restClient.get()
            .uri(requestUrl)
            .accept(MediaType.APPLICATION_JSON)
            .exchange { _, response ->
                if (response.statusCode.is4xxClientError) ""
                else response.bodyTo(NaverLoginAuthResponse::class.java)!!.accessToken
            }
    }

    private fun getPersonalInformation(accessToken: String): String {
        return RestClient.create().let { restClient ->
            val personalInfoRequestUrl = "https://openapi.naver.com/v1/nid/me"

            val requestHeaders: HttpHeaders = HttpHeaders()
            requestHeaders.contentType = MediaType.APPLICATION_JSON
            requestHeaders.set("Authorization", "Bearer ${accessToken}")

            val personalInfoRequestResult = restClient.get()
                .uri(personalInfoRequestUrl)
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                    "Bro"
                }
                .body(String::class.java)

            personalInfoRequestResult!!
        }
    }

    fun checkValidity(code: String, state: String): String {
        if (stateTokens.find{ it == state } == null) return "Is it forged?"
        stateTokens.remove(state)

        val accessTokenRequestUrl = "https://nid.naver.com/oauth2.0/token?" +
                "grant_type=authorization_code&" +
                "client_id=${clientId}&" +
                "client_secret=${clientSecret}&" +
                "code=${code}&" +
                "state=${state}"

        val personalInfoRequestUrl = "https://openapi.naver.com/v1/nid/me"

        val restClient: RestClient = RestClient.create()

        val accessTokenRequested = restClient.get()
            .uri(accessTokenRequestUrl)
            .accept(MediaType.APPLICATION_JSON)
            .exchange { _, response ->
                if (response.statusCode.is4xxClientError) "Bruh"
                else response.bodyTo(NaverLoginAuthResponse::class.java)!!.accessToken
            }


        val requestHeaders: HttpHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON
        requestHeaders.set("Authorization", "Bearer ${accessTokenRequested}")

        val personalInfoRequestResult = restClient.get()
            .uri(personalInfoRequestUrl)
            .header("Authorization", "Bearer $accessTokenRequested")
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
               "Bro"
            }
            .body(String::class.java)

        return personalInfoRequestResult!!
    }

}