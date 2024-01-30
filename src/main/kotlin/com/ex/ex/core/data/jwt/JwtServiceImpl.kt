package com.ex.ex.core.data.jwt

import com.ex.ex.core.data.jwt.entity.JwtEntity
import com.ex.ex.core.data.jwt.model.JwtModel
import com.ex.ex.core.data.jwt.model.JwtType
import com.ex.ex.core.exception.ExpiredJwtException
import com.ex.ex.core.exception.ForbiddenException
import com.ex.ex.property.SecurityProperty
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.Date
import java.util.concurrent.TimeUnit
import java.util.function.Function
import javax.crypto.SecretKey
import kotlin.collections.HashMap

@Service
@RequiredArgsConstructor
class JwtServiceImpl(private val mSecurityProperty: SecurityProperty) : JwtService {

    private val mLogger by lazy { LoggerFactory.getLogger(this::class.java) }

    override fun setToken(jwtEntity: JwtEntity): JwtModel {
        val jwtModel = JwtModel()

        val claims = jwtEntity.map()
        if (jwtEntity.jwtType == JwtType.REFRESH) throw ForbiddenException("Request jwt type is forbidden.")

        jwtModel.accessToken = generateToken(claims, JwtType.ACCESS)
        jwtModel.refreshToken = generateToken(claims, JwtType.REFRESH)

        return jwtModel
    }

    override fun getToken(token: String): JwtModel {
        val jwtModel = JwtModel()

        try {
            val claims = getAllClaims(token) { it }
            if (claims == null) {
                jwtModel.isInvalid = true

                return jwtModel
            }

            val userId = claims.get(JwtEntity.USER_ID, String::class.java)
            val jwtType = claims.get(JwtEntity.JWT_TYPE, String::class.java)

            jwtModel.userId = userId.toLong()
            jwtModel.jwtType = JwtType.valueOf(jwtType)
        } catch (e: Exception) {
            mLogger.error("Error on validate jwt token : ${e.localizedMessage}")

            jwtModel.isExpired = e is ExpiredJwtException
        }

        return jwtModel
    }

    private fun generateToken(claims: HashMap<String, String?>, jwtType: JwtType): String {
        val currentTime = System.currentTimeMillis()
        val expireTime = when (jwtType) {
            JwtType.ACCESS -> TimeUnit.HOURS.toMillis(1)
            JwtType.REFRESH -> TimeUnit.DAYS.toMillis(30)
        }

        claims[JwtEntity.JWT_TYPE] = jwtType.name

        return Jwts.builder()
            .claims(claims)
            .issuedAt(Date(currentTime))
            .expiration(Date(currentTime + expireTime))
            .signWith(getSignInKey())
            .compact()
    }

    private fun <T> getAllClaims(token: String, claimResolver: Function<Claims, T>): T? {
        val claims = Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload

        return claimResolver.apply(claims)
    }

    private fun getSignInKey(): SecretKey? {
        val key: ByteArray
        try {
            key = Decoders.BASE64.decode(mSecurityProperty.jwtPassword)
        } catch (e: Exception) {
            mLogger.error("Error on initialize sign in key : ${e.localizedMessage}")

            return null
        }

        return Keys.hmacShaKeyFor(key)
    }

}