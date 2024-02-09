package com.ex.ex.core.data.transaction

import com.ex.ex.core.data.transaction.entity.TransactionEntity
import com.ex.ex.core.data.transaction.model.TransactionModel
import com.ex.ex.core.data.transaction.model.TransactionStatus

interface TransactionService {

    fun getListTransactionByListId(listTransactionId: List<Long>): List<TransactionModel>

    fun setTransaction(transactionEntity: TransactionEntity): TransactionModel

    fun setTransactionStatus(transactionId: Long?, transactionStatus: TransactionStatus): TransactionModel

}