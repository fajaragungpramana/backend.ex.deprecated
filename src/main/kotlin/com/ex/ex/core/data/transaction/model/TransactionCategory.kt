package com.ex.ex.core.data.transaction.model

enum class TransactionCategory(val id: Long?) {
    HOUSE(1),
    TRANSPORTATION(2),
    FOOD(3),
    DRINK(4),
    UTILITY(5),
    INSURANCE(6),
    MEDICAL_AND_HEALTH_CARE(7),
    SAVING_AND_INVESTING(8),
    DEPT(9),
    PERSONAL_SPENDING(10),
    RECREATION_AND_ENTERTAINMENT(11),
    MISCELLANEOUS(12),
    TOP_UP(13);

    companion object {

        fun find(id: Long?) = entries.find { it.id == id }

    }
}