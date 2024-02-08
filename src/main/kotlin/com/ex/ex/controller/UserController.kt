package com.ex.ex.controller

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.constant.HttpRoute
import com.ex.ex.core.data.wallet.entity.WalletEntity
import com.ex.ex.core.domain.user.UserUseCase
import com.ex.ex.core.domain.user.response.UserResponse
import com.ex.ex.core.domain.wallet.WalletUseCase
import com.ex.ex.core.domain.wallet.request.WalletRequest
import com.ex.ex.core.domain.wallet.response.WalletResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(HttpRoute.V_1 + HttpRoute.USER)
@RequiredArgsConstructor
class UserController(private val mUserUseCase: UserUseCase, private val mWalletUseCase: WalletUseCase) {

    @GetMapping(HttpRoute.ME)
    fun onUser(): ResponseEntity<ApplicationResponse<UserResponse>> {
        val authentication = SecurityContextHolder.getContext().authentication

        return mUserUseCase.onUser(authentication.name.toLong())
    }

    @PostMapping(HttpRoute.WALLET)
    fun setWallet(@RequestBody walletRequest: WalletRequest): ResponseEntity<ApplicationResponse<WalletResponse>> {
        val responseBody = ApplicationResponse<WalletResponse>()
        if (walletRequest.name.isNullOrEmpty()) {
            responseBody.message = "Request name is required."
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        if (walletRequest.typeId == null) {
            responseBody.message = "Request type_id is required."
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        val authentication = SecurityContextHolder.getContext().authentication

        return mWalletUseCase.setWallet(WalletEntity(
            userId = authentication.name.toLong(),
            balance = 0,
            name = walletRequest.name,
            typeId = walletRequest.typeId,
            createdAt = System.currentTimeMillis()
        ))
    }

    @GetMapping(HttpRoute.WALLET)
    fun getListWallet(): ResponseEntity<ApplicationResponse<List<WalletResponse>>> {
        val authentication = SecurityContextHolder.getContext().authentication

        return mWalletUseCase.getListWallet(authentication.name.toLong())
    }

}