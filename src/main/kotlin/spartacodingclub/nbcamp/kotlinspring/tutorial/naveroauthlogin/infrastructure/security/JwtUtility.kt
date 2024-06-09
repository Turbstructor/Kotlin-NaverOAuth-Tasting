package spartacodingclub.nbcamp.kotlinspring.tutorial.naveroauthlogin.infrastructure.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

@Component
class JwtUtility(
    @Value("\${jwt.issuer}")
    private val issuer: String,

    @Value("\${jwt.secret}")
    private val secret: String,

    @Value("\${jwt.expirationHours.access}")
    private val expirationHoursAccess: Long
) {

    private val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    fun generateAccessToken(id: Long): String =
        generateToken(id, Duration.ofHours(expirationHoursAccess))

    private fun generateToken(id: Long, expirationHours: Duration): String =
        Jwts.claims().add( mapOf("id" to id) ).build().let { claims ->
            LocalDateTime.now().toInstant(ZoneOffset.UTC).let { currentTime ->
                Jwts.builder()
                    .issuer(issuer).claims(claims)
                    .issuedAt(Date.from(currentTime))
                    .expiration(Date.from(currentTime.plus(expirationHours)))
                    .signWith(key)
                    .compact()
            }
        }

}