package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.client.naver

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.client.OAuth2Client
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.client.naver.dto.response.NaverOAuth2LoginResponse
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.client.naver.dto.response.NaverUserInfoQueryResponse
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.dto.SocialLoginInfo
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.dto.from
import spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.auth.oauth.type.OAuth2Provider
import java.math.BigInteger
import java.net.URLEncoder
import java.security.SecureRandom

@Component
class NaverOAuth2Client(

    @Value("\${oauth2.naver.client.id}")
    private val clientId: String,

    @Value("\${oauth2.naver.client.secret}")
    private val clientSecret: String,

    @Value("\${oauth2.naver.redirect_url}")
    private val redirectUrl: String
): OAuth2Client {

    private final val personalInfoRequestUrl = "https://openapi.naver.com/v1/nid/me"
    private final val oAuthRequestUrl = "https://nid.naver.com/oauth2.0"

    private var stateTokens: MutableList<String> = mutableListOf()


    private fun generateState(): String = BigInteger(130, SecureRandom()).toString(32)
    private fun isStateIssued(stateToken: String): Boolean = (stateTokens.find { it == stateToken } != null)


    override fun supports(provider: OAuth2Provider): Boolean = (provider == OAuth2Provider.NAVER)

    override fun socialLoginUrl(): String {
        return generateState().let {
            stateTokens.add(it)

            "${oAuthRequestUrl}/authorize?response_type=code" +
                    "&client_id=${clientId}" +
                    "&redirect_uri=${URLEncoder.encode(redirectUrl, "UTF-8")}" +
                    "&state=${it}"
        }
    }

    override fun getAccessToken(stateToken: String, authorizationCode: String): String {
        if (!isStateIssued(stateToken)) throw IllegalArgumentException("State forged")

        val requestUrl = "${oAuthRequestUrl}/token?" +
                "grant_type=authorization_code&" +
                "client_id=${clientId}&client_secret=${clientSecret}&" +
                "code=${authorizationCode}&state=${stateToken}"

        return RestClient.create().let {
            it.get()
                .uri(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError) { _, _ -> throw RuntimeException("Failed to retrieve access token") }
                .body(NaverOAuth2LoginResponse::class.java)!!
                .accessToken
        }
    }

    override fun getUserInfo(accessToken: String): SocialLoginInfo {
        val restClient = RestClient.create()

        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON
        requestHeaders.set("Authorization", "Bearer $accessToken")

        return SocialLoginInfo.from(restClient.get()
            .uri(personalInfoRequestUrl)
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .onStatus(HttpStatusCode::isError) { _, _ -> throw RuntimeException("Failed to query personal info") }
            .body(NaverUserInfoQueryResponse::class.java)!!
        )
    }

}