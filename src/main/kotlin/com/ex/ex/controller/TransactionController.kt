package com.ex.ex.controller

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.constant.HttpRoute
import com.ex.ex.core.domain.transaction.TransactionUseCase
import com.ex.ex.core.domain.transaction.response.TransactionResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(HttpRoute.V_1 + HttpRoute.TRANSACTION)
@RequiredArgsConstructor
class TransactionController(private val mTransactionUseCase: TransactionUseCase) {

    @GetMapping(HttpRoute.WALLET + "/{id}")
    fun getListTransactionByWallet(
        @PathVariable id: String?,
        @RequestParam page: Int = 1,
        @RequestParam size: Int = 15
    ): ResponseEntity<ApplicationResponse<List<TransactionResponse>>> {
        val responseBody = ApplicationResponse<List<TransactionResponse>>()
        if (id.isNullOrEmpty()) {
            responseBody.message = "Path variable id is required."
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
        }

        return mTransactionUseCase.getListTransactionByWalletId(
            walletId = id.toLong(),
            page = page,
            size = size
        )
    }

}