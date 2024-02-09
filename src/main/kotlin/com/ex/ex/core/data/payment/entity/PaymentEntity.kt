package com.ex.ex.core.data.payment.entity

import com.ex.ex.core.data.DataConstant
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = DataConstant.TABLE_NAME_PAYMENTS)
data class PaymentEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "transaction_id")
    val transactionId: Long? = null,

    @Column(name = "amount")
    val amount: Long? = null,

    @Column(name = "status_id")
    val statusId: Long? = null,

    @Column(name = "type_id")
    val typeId: Long? = null,

    @Column(name = "wallet_id")
    val walletId: Long? = null,

    @Column(name = "created_at")
    val createdAt: Long? = null,

    @Column(name = "updated_at")
    val updatedAt: Long? = null,

    @Column(name = "deleted_at")
    val deletedAt: Long? = null

)