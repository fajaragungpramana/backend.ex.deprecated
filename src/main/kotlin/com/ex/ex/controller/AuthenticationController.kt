package com.ex.ex.controller

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.constant.HttpRoute
import com.ex.ex.core.domain.authentication.AuthenticationUseCase
import com.ex.ex.core.domain.authentication.request.LoginRequest
import com.ex.ex.core.domain.authentication.request.RegisterRequest
import com.ex.ex.core.domain.authentication.response.LoginResponse
import com.ex.ex.extension.isEmail
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(HttpRoute.V_1 + HttpRoute.AUTHENTICATION)
@RequiredArgsConstructor
class AuthenticationController(private val mAuthenticationUseCase: AuthenticationUseCase) {

    @PostMapping(HttpRoute.LOGIN)
    fun onLogin(@RequestBody loginRequest: LoginRequest): ResponseEntity<ApplicationResponse<LoginResponse>> {
        val responseBody = ApplicationResponse<LoginResponse>()
        if (loginRequest.email.isNullOrEmpty()) {
            responseBody.message = "Request email is required."

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        if (!loginRequest.email.isEmail()) {
            responseBody.message = "Request email is not valid."

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        if (loginRequest.password.isNullOrEmpty()) {
            responseBody.message = "Request password is required."

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        return mAuthenticationUseCase.onLogin(loginRequest)
    }

    @PostMapping(HttpRoute.REGISTER)
    fun onRegister(@RequestBody registerRequest: RegisterRequest): ResponseEntity<ApplicationResponse<Boolean>> {
        val responseBody = ApplicationResponse<Boolean>()
        if (registerRequest.fullName.isNullOrEmpty()) {
            responseBody.message = "Request full_name is required."

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        if (registerRequest.fullName.length < 4) {
            responseBody.message = "Request full_name min 4 character."

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        if (registerRequest.email.isNullOrEmpty()) {
            responseBody.message = "Request email is required."

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        if (!registerRequest.email.isEmail()) {
            responseBody.message = "Request email is not valid."

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        if (registerRequest.password.isNullOrEmpty()) {
            responseBody.message = "Request password is not valid."

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        if (registerRequest.password.length < 8) {
            responseBody.message = "Request password min 8 character."

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        return mAuthenticationUseCase.onRegister(registerRequest)
    }

}