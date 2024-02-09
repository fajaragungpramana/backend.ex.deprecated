package com.ex.ex.core.data.payment.model

enum class PaymentType(val id: Long?) {
    CREDIT(3), DEBIT(4);

    companion object {
        fun find(id: Long?) = entries.find { it.id == id }

    }
}