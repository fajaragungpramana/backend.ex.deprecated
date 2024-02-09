package com.ex.ex.core.data.transaction.model

enum class TransactionStatus(val id: Long?) {
    SUCCESS(1), PENDING(2), FAILURE(3);

    companion object {

        fun find(id: Long?) = entries.find { it.id == id }

    }
}