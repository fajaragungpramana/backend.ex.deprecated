package com.ex.ex.core.domain.authorization

import com.ex.ex.core.data.jwt.JwtService
import com.ex.ex.core.data.jwt.model.JwtType
import com.ex.ex.core.data.user.UserService
import com.ex.ex.core.domain.authorization.response.AuthorizationResponse
import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class AuthorizationInteractor(private val mUserService: UserService, private val mJwtService: JwtService) :
    AuthorizationUseCase {

    override fun getToken(token: String): AuthorizationResponse {
        val responseBody = AuthorizationResponse()

        val jwt = mJwtService.getToken(token)
        if (jwt.isInvalid == true) {
            responseBody.isJwtValid = false

            return responseBody
        }

        if (jwt.jwtType == JwtType.REFRESH) {
            responseBody.isJwtGranted = false

            return responseBody
        }

        if (jwt.isExpired == true) {
            responseBody.isJwtExpired = false

            return responseBody
        }

        responseBody.userId = jwt.userId
        responseBody.jwtType = jwt.jwtType

        return responseBody
    }

    override fun getUser(id: Long?): UserDetails = mUserService.loadUserByUsername(id.toString())

}