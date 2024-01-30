package com.ex.ex.core.domain.authentication

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.domain.authentication.request.LoginRequest
import com.ex.ex.core.domain.authentication.response.LoginResponse
import org.springframework.http.ResponseEntity

interface AuthenticationUseCase {

    fun onLogin(loginRequest: LoginRequest): ResponseEntity<ApplicationResponse<LoginResponse>>

}