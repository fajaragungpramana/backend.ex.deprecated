package com.ex.ex.core.data.wallet.model

data class WalletModel(
    var id: Long? = null,
    var name: String? = null,
    var type: WalletType? = null,
    var balance: Long? = null,
    var createdAt: Long? = null
)
