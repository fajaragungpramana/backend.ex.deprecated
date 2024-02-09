package com.ex.ex.core.data.wallet

import com.ex.ex.core.data.wallet.entity.WalletEntity
import com.ex.ex.core.data.wallet.model.WalletModel

interface WalletService {

    fun setWallet(walletEntity: WalletEntity): WalletModel

    fun setWalletBalance(walletEntity: WalletEntity, isDebit: Boolean): WalletModel

    fun getListWallet(userId: Long?): List<WalletModel>

    fun getWallet(walletEntity: WalletEntity): WalletModel

}