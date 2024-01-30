package com.ex.ex.core.domain.authentication

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.data.jwt.JwtService
import com.ex.ex.core.data.jwt.entity.JwtEntity
import com.ex.ex.core.data.jwt.model.JwtType
import com.ex.ex.core.data.user.UserService
import com.ex.ex.core.data.user.entity.UserEntity
import com.ex.ex.core.domain.authentication.request.LoginRequest
import com.ex.ex.core.domain.authentication.request.RegisterRequest
import com.ex.ex.core.domain.authentication.response.LoginResponse
import com.ex.ex.core.exception.ForbiddenException
import com.ex.ex.core.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class AuthenticationInteractor(private val mUserService: UserService, private val mJwtService: JwtService) :
    AuthenticationUseCase {

    override fun onLogin(loginRequest: LoginRequest): ResponseEntity<ApplicationResponse<LoginResponse>> {
        val responseBody = ApplicationResponse<LoginResponse>()

        try {
            val userLogin = mUserService.login(UserEntity(email = loginRequest.email, password = loginRequest.password))
            val jwt = mJwtService.setToken(JwtEntity(userId = userLogin.id, jwtType = JwtType.ACCESS))

            responseBody.message = "User login successfully."
            responseBody.data = LoginResponse(
                accessToken = jwt.accessToken,
                refreshToken = jwt.refreshToken
            )
        } catch (e: Exception) {
            responseBody.message = e.message

            return ResponseEntity.status(
                when (e) {
                    is ForbiddenException -> HttpStatus.FORBIDDEN
                    is NotFoundException -> HttpStatus.NOT_FOUND

                    else -> HttpStatus.INTERNAL_SERVER_ERROR
                }
            ).body(responseBody)
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody)
    }

    override fun onRegister(registerRequest: RegisterRequest): ResponseEntity<ApplicationResponse<Boolean>> {
        val responseBody = ApplicationResponse<Boolean>()

        try {
            val userRegister = mUserService.register(UserEntity(
                fullName = registerRequest.fullName,
                email = registerRequest.email,
                password = registerRequest.password,
                createdAt = System.currentTimeMillis()
            ))

            responseBody.message = "User registration successfully."
            responseBody.data = userRegister.id != null
        } catch (e: Exception) {
            responseBody.message = e.message

            return ResponseEntity.status(
                when (e) {
                    is ForbiddenException -> HttpStatus.FORBIDDEN

                    else -> HttpStatus.INTERNAL_SERVER_ERROR
                }
            ).body(responseBody)
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody)
    }

}