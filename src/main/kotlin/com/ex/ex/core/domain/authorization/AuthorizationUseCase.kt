package com.ex.ex.core.domain.authorization

import com.ex.ex.core.domain.authorization.response.AuthorizationResponse
import org.springframework.security.core.userdetails.UserDetails

interface AuthorizationUseCase {

    fun getToken(token: String): AuthorizationResponse

    fun getUser(id: Long?): UserDetails

}