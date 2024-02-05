package com.ex.ex.core.data.wallet

import com.ex.ex.core.data.wallet.entity.WalletEntity
import com.ex.ex.core.data.wallet.model.WalletModel

interface WalletService {

    fun setWallet(walletEntity: WalletEntity): WalletModel

}