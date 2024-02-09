package com.ex.ex.core.data.transaction

import com.ex.ex.core.data.transaction.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<TransactionEntity, Long> {

    fun findByIdInAndDeletedAt(id: List<Long>, deletedAt: Long?): List<TransactionEntity>?

}