package com.ex.ex.core.data.wallet.model

enum class WalletType(val id: Long?) {
    CASH(5),
    BANK_ACCOUNT(6),
    E_WALLET(7),
    E_MONEY(8);

    companion object {

        fun find(id: Long?) = entries.find { it.id == id }

    }

}