package com.ex.ex.core.domain.wallet

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.data.wallet.WalletService
import com.ex.ex.core.data.wallet.entity.WalletEntity
import com.ex.ex.core.domain.wallet.response.WalletResponse
import com.ex.ex.core.exception.ForbiddenException
import com.ex.ex.core.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class WalletInteractor(private val mWalletService: WalletService) : WalletUseCase {

    override fun setWallet(walletEntity: WalletEntity): ResponseEntity<ApplicationResponse<WalletResponse>> {
        val responseBody = ApplicationResponse<WalletResponse>()
        try {
            val wallet = mWalletService.setWallet(walletEntity)
            if (wallet.id != null)
                responseBody.message = "Wallet type ${wallet.type?.name} with name ${wallet.name} has been created."

        } catch (e: Exception) {
            responseBody.message = e.message

            return ResponseEntity.status(
                when (e) {
                    is ForbiddenException -> HttpStatus.FORBIDDEN

                    else -> HttpStatus.INTERNAL_SERVER_ERROR
                }
            ).body(responseBody)
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody)
    }

    override fun getListWallet(userId: Long?): ResponseEntity<ApplicationResponse<List<WalletResponse>>> {
        val responseBody = ApplicationResponse<List<WalletResponse>>()

        try {
            val listWalletResponse = arrayListOf<WalletResponse>()
            val listWalletModel = mWalletService.getListWallet(userId)
            listWalletModel.forEach {
                listWalletResponse.add(
                    WalletResponse(
                        name = it.name,
                        type = it.type?.name,
                        balance = it.balance,
                        createdAt = it.createdAt
                    )
                )
            }

            responseBody.message = "Wallet found."
            responseBody.data = listWalletResponse
        } catch (e: Exception) {
            responseBody.message = e.message

            return ResponseEntity.status(
                when (e) {
                    is NotFoundException -> HttpStatus.FORBIDDEN

                    else -> HttpStatus.INTERNAL_SERVER_ERROR
                }
            ).body(responseBody)
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody)
    }

}