package com.ex.ex.controller

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.constant.HttpRoute
import com.ex.ex.core.domain.authentication.AuthenticationUseCase
import com.ex.ex.core.domain.authentication.request.LoginRequest
import com.ex.ex.core.domain.authentication.response.LoginResponse
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

        if (loginRequest.password.isNullOrEmpty()) {
            responseBody.message = "Request password is required."

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        return mAuthenticationUseCase.onLogin(loginRequest)
    }

}