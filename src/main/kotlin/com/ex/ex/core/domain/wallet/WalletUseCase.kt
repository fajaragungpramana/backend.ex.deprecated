package com.ex.ex.core.domain.wallet

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.data.wallet.entity.WalletEntity
import com.ex.ex.core.domain.wallet.response.WalletResponse
import org.springframework.http.ResponseEntity

interface WalletUseCase {

    fun setWallet(walletEntity: WalletEntity): ResponseEntity<ApplicationResponse<WalletResponse>>

}