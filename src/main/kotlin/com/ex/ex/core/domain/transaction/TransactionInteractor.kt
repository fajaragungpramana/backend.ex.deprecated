package com.ex.ex.core.domain.transaction

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.data.payment.PaymentService
import com.ex.ex.core.data.payment.entity.PaymentEntity
import com.ex.ex.core.data.transaction.TransactionService
import com.ex.ex.core.domain.transaction.response.TransactionResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class TransactionInteractor(
    private val mTransactionService: TransactionService,
    private val mPaymentService: PaymentService
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

}