package com.ex.ex.core.domain.user

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.domain.user.response.UserResponse
import org.springframework.http.ResponseEntity

interface UserUseCase {

    fun onUser(id: Long?): ResponseEntity<ApplicationResponse<UserResponse>>

}