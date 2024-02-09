package com.ex.ex.core.domain.transaction

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.data.payment.PaymentService
import com.ex.ex.core.data.payment.entity.PaymentEntity
import com.ex.ex.core.data.payment.model.PaymentStatus
import com.ex.ex.core.data.payment.model.PaymentType
import com.ex.ex.core.data.transaction.TransactionService
import com.ex.ex.core.data.transaction.entity.TransactionEntity
import com.ex.ex.core.data.transaction.model.TransactionCategory
import com.ex.ex.core.data.transaction.model.TransactionStatus
import com.ex.ex.core.data.transaction.model.TransactionType
import com.ex.ex.core.data.wallet.WalletService
import com.ex.ex.core.data.wallet.entity.WalletEntity
import com.ex.ex.core.domain.transaction.request.TransactionRequest
import com.ex.ex.core.domain.transaction.response.TransactionResponse
import com.ex.ex.core.exception.ForbiddenException
import com.ex.ex.core.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class TransactionInteractor(
    private val mTransactionService: TransactionService,
    private val mPaymentService: PaymentService,
    private val mWalletService: WalletService
) : TransactionUseCase {

    override fun getListTransactionByWalletId(
        walletId: Long?,
        page: Int,
        size: Int
    ): ResponseEntity<ApplicationResponse<List<TransactionResponse>>> {
        val responseBody = ApplicationResponse<List<TransactionResponse>>()

        try {
            val listTransactionResponse = arrayListOf<TransactionResponse>()
            responseBody.message = "Transaction found."

            val listPayment =
                mPaymentService.getListPaymentByWalletId(PaymentEntity(walletId = walletId), page, size)
            if (listPayment.isNotEmpty()) {
                val listTransactionEntity = arrayListOf<Long>()
                listPayment.forEach {
                    val id = it.transactionId
                    if (id != null) listTransactionEntity.add(id)
                }

                val listTransaction = mTransactionService.getListTransactionByListId(listTransactionEntity)
                if (listTransaction.isNotEmpty()) {
                    for (i in listPayment.indices) {
                        val payment = listPayment[i]
                        val transaction = listTransaction[i]

                        if (payment.transactionId == transaction.id)
                            listTransactionResponse.add(
                                TransactionResponse(
                                    name = transaction.name,
                                    description = transaction.description,
                                    type = transaction.type?.name,
                                    category = transaction.category?.name,
                                    status = transaction.status?.name,
                                    payment = TransactionResponse.Payment(
                                        amount = payment.amount,
                                        status = payment.status?.name,
                                        type = payment.type?.name,
                                        createdAt = payment.createdAt
                                    ),
                                    createdAt = transaction.createdAt
                                )
                            )
                    }
                }
            }

            responseBody.data = listTransactionResponse

        } catch (e: Exception) {
            responseBody.message = e.message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody)
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody)
    }

    override fun setTransaction(
        userId: Long,
        transactionRequest: TransactionRequest
    ): ResponseEntity<ApplicationResponse<TransactionResponse>> {
        val responseBody = ApplicationResponse<TransactionResponse>()
        val amount = transactionRequest.amount ?: 0
        if (amount <= 0) {
            responseBody.message = "Transaction amount should greater than 0"

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody)
        }

        try {
            val transactionCategory = TransactionCategory.find(transactionRequest.categoryId)
            val transactionType =
                if (transactionCategory == TransactionCategory.TOP_UP) TransactionType.DEBIT else TransactionType.CREDIT

            if (transactionCategory != TransactionCategory.TOP_UP) {
                mWalletService.setWalletBalance(
                    WalletEntity(
                        id = transactionRequest.walletId,
                        userId = userId,
                        balance = amount
                    ),
                    false
                )
            }

            val transaction = mTransactionService.setTransaction(
                TransactionEntity(
                    userId = userId,
                    name = transactionRequest.name,
                    description = transactionRequest.description,
                    categoryId = transactionRequest.categoryId,
                    statusId = TransactionStatus.PENDING.id,
                    typeId = transactionType.id,
                    createdAt = System.currentTimeMillis()
                )
            )
            if (transaction.id != null) {
                val paymentType =
                    if (transactionCategory == TransactionCategory.TOP_UP) PaymentType.DEBIT else PaymentType.CREDIT

                val payment = mPaymentService.setPayment(
                    PaymentEntity(
                        transactionId = transaction.id,
                        amount = amount,
                        statusId = PaymentStatus.SUCCESS.id,
                        walletId = transactionRequest.walletId,
                        typeId = paymentType.id,
                        createdAt = System.currentTimeMillis()
                    )
                )

                val updatedTransaction = mTransactionService.setTransactionStatus(
                    transactionId = transaction.id,
                    if (payment.id == null) TransactionStatus.FAILURE else TransactionStatus.SUCCESS
                )

                when (updatedTransaction.status) {
                    TransactionStatus.SUCCESS -> if (transactionCategory == TransactionCategory.TOP_UP)
                        mWalletService.setWalletBalance(
                            WalletEntity(
                                id = transactionRequest.walletId,
                                userId = userId,
                                balance = transactionRequest.amount
                            ),
                            true
                        )

                    TransactionStatus.FAILURE -> if (transactionCategory != TransactionCategory.TOP_UP)
                        mWalletService.setWalletBalance(
                            WalletEntity(
                                id = transactionRequest.walletId,
                                userId = userId,
                                balance = transactionRequest.amount
                            ),
                            true
                        )

                    else -> {}
                }

                responseBody.message = "Transaction has been created"
                responseBody.data = TransactionResponse(
                    name = updatedTransaction.name,
                    description = updatedTransaction.description,
                    type = updatedTransaction.type?.name,
                    category = updatedTransaction.category?.name,
                    status = updatedTransaction.status?.name,
                    payment = TransactionResponse.Payment(
                        amount = payment.amount,
                        status = payment.status?.name,
                        type = payment.type?.name,
                        createdAt = payment.createdAt
                    ),
                    createdAt = updatedTransaction.createdAt
                )
            }

        } catch (e: Exception) {
            responseBody.message = e.message
            return ResponseEntity.status(
                when (e) {
                    is ForbiddenException -> HttpStatus.FORBIDDEN
                    is NotFoundException -> HttpStatus.NOT_FOUND

                    else -> HttpStatus.INTERNAL_SERVER_ERROR
                }
            ).body(responseBody)
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody)
    }

}