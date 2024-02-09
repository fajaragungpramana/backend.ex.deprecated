package com.ex.ex.core.data.payment

import com.ex.ex.core.data.payment.entity.PaymentEntity
import com.ex.ex.core.data.payment.model.PaymentModel

interface PaymentService {

    fun getListPaymentByWalletId(paymentEntity: PaymentEntity, page: Int, size: Int) : List<PaymentModel>

}