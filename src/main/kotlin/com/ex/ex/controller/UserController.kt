package com.ex.ex.controller

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.constant.HttpRoute
import com.ex.ex.core.domain.user.UserUseCase
import com.ex.ex.core.domain.user.response.UserResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(HttpRoute.V_1 + HttpRoute.USER)
@RequiredArgsConstructor
class UserController(private val mUserUseCase: UserUseCase) {

    @GetMapping(HttpRoute.ME)
    fun onUser(): ResponseEntity<ApplicationResponse<UserResponse>> {
        val authentication = SecurityContextHolder.getContext().authentication

        return mUserUseCase.onUser(authentication.name.toLong())
    }

}