package com.ex.ex.core.data.payment.model

enum class PaymentStatus(val id: Long?) {
    SUCCESS(4), PENDING(5), FAILURE(6);

    companion object {

        fun find(id: Long?) = entries.find { it.id == id }

    }
}