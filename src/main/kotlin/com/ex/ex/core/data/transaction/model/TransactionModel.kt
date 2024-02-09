package com.ex.ex.core.data.transaction.model

data class TransactionModel(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var category: TransactionCategory? = null,
    var status: TransactionStatus? = null,
    var type: TransactionType? = null,
    var createdAt: Long? = null
)