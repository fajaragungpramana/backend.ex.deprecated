package com.ex.ex.core.data.payment.model

data class PaymentModel(
    var transactionId: Long? = null,
    var amount: Long? = null,
    var status: PaymentStatus? = null,
    var type: PaymentType? = null,
    var createdAt: Long? = null
)