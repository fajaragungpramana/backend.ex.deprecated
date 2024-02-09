package com.ex.ex.core.domain.transaction

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.domain.transaction.request.TransactionRequest
import com.ex.ex.core.domain.transaction.response.TransactionResponse
import org.springframework.http.ResponseEntity

interface TransactionUseCase {

    fun getListTransactionByWalletId(
        walletId: Long?,
        page: Int,
        size: Int
    ): ResponseEntity<ApplicationResponse<List<TransactionResponse>>>

}