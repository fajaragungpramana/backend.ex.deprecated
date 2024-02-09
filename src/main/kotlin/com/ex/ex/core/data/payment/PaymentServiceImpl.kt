package com.ex.ex.core.data.payment

import com.ex.ex.core.data.payment.entity.PaymentEntity
import com.ex.ex.core.data.payment.model.PaymentModel
import com.ex.ex.core.data.payment.model.PaymentStatus
import com.ex.ex.core.data.payment.model.PaymentType
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class PaymentServiceImpl(private val mPaymentRepository: PaymentRepository) : PaymentService {

    override fun getListPaymentByWalletId(paymentEntity: PaymentEntity, page: Int, size: Int): List<PaymentModel> {
        if (paymentEntity.walletId == null) throw NullPointerException("Payment wallet_id is required.")

        val listPaymentModel = arrayListOf<PaymentModel>()

        val listPayment = mPaymentRepository.findByWalletIdAndDeletedAtOrderByUpdatedAtDesc(
            paymentEntity.walletId,
            null,
            PageRequest.of(page, size)
        )
        if (listPayment.isEmpty) return listPaymentModel

        listPayment.forEach { data ->
            if (data.deletedAt == null)
                listPaymentModel.add(
                    PaymentModel(
                        transactionId = data.transactionId,
                        amount = data.amount,
                        status = PaymentStatus.find(data.statusId),
                        type = PaymentType.find(data.typeId),
                        createdAt = data.createdAt
                    )
                )
        }

        return listPaymentModel
    }

    override fun setPayment(paymentEntity: PaymentEntity): PaymentModel {
        val paymentModel = PaymentModel()

        val payment = mPaymentRepository.save(paymentEntity)
        paymentModel.let {
            it.id = payment.id
            it.transactionId = payment.transactionId
            it.amount = payment.amount
            it.status = PaymentStatus.find(payment.statusId)
            it.type = PaymentType.find(payment.typeId)
            it.createdAt = it.createdAt
        }

        return paymentModel
    }

}