package com.ex.ex.core.data.payment

import com.ex.ex.core.data.payment.entity.PaymentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<PaymentEntity, Long> {

    fun findByWalletIdAndDeletedAtOrderByUpdatedAtDesc(
        walletId: Long,
        deletedAt: Long?,
        pageable: Pageable
    ): Page<PaymentEntity>

}