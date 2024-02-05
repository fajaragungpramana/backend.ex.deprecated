package com.ex.ex.core.data.wallet

import com.ex.ex.core.data.wallet.entity.WalletEntity
import com.ex.ex.core.data.wallet.model.WalletModel
import com.ex.ex.core.data.wallet.model.WalletType
import com.ex.ex.core.exception.ForbiddenException
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class WalletServiceImpl(private val mWalletRepository: WalletRepository) : WalletService {

    override fun setWallet(walletEntity: WalletEntity): WalletModel {
        if (walletEntity.userId == null) throw NullPointerException("User id is required.")
        if (WalletType.find(walletEntity.typeId) == null) throw  ForbiddenException("Wallet with provided type_id is forbidden.")

        val walletModel = WalletModel()
        mWalletRepository.save(walletEntity).let {
            walletModel.id = it.id
            walletModel.name = it.name
            walletModel.type = WalletType.find(it.typeId)
        }

        return walletModel
    }

}