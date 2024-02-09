package com.ex.ex.core.data.transaction

import com.ex.ex.core.data.transaction.model.TransactionModel

interface TransactionService {

    fun getListTransactionByListId(listTransactionId: List<Long>): List<TransactionModel>

}