package com.ex.ex.core.data.transaction.model

enum class TransactionType(val id: Long?) {
    CREDIT(1), DEBIT(2);

    companion object {

        fun find(id: Long?) = entries.find { it.id == id }

    }
}