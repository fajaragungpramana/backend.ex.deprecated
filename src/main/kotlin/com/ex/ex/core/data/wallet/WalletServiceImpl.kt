package com.ex.ex.core.data.wallet

import com.ex.ex.core.data.wallet.entity.WalletEntity
import com.ex.ex.core.data.wallet.model.WalletModel
import com.ex.ex.core.data.wallet.model.WalletType
import com.ex.ex.core.exception.ForbiddenException
import com.ex.ex.core.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class WalletServiceImpl(private val mWalletRepository: WalletRepository) : WalletService {

    override fun setWallet(walletEntity: WalletEntity): WalletModel {
        if (walletEntity.userId == null) throw NullPointerException("User id is required.")
        if (WalletType.find(walletEntity.typeId) == null) throw ForbiddenException("Wallet with provided type_id is forbidden.")

        val walletModel = WalletModel()
        mWalletRepository.save(walletEntity).let {
            walletModel.id = it.id
            walletModel.name = it.name
            walletModel.type = WalletType.find(it.typeId)
        }

        return walletModel
    }

    override fun setWalletBalance(walletEntity: WalletEntity, isDebit: Boolean): WalletModel {
        val id = walletEntity.id
        val userId = walletEntity.userId
        val amount = walletEntity.balance ?: 0
        if (id == null && userId == null) throw NullPointerException("Wallet id and user_id is required.")
        if (amount <= 0) throw ForbiddenException("Transaction amount should greater than 0")

        val walletModel = WalletModel()

        val wallet = mWalletRepository.findByIdAndUserIdAndDeletedAt(id ?: 0, userId = userId ?: 0, null)
        if (wallet == null) throw NotFoundException("Wallet with provided id not found.")

        if (wallet.deletedAt != null) throw NotFoundException("Wallet with provided id not found.")

        val balance = wallet.balance ?: 0
        if (!isDebit && balance < amount) throw ForbiddenException("Balance is not enough.")

        val transactionAmount = if (isDebit) balance + amount else balance - amount

        val transactionWallet = mWalletRepository.save(wallet.copy(balance = transactionAmount))
        walletModel.let {
            it.id = transactionWallet.id
            it.name = transactionWallet.name
            it.balance = transactionWallet.balance
            it.type = WalletType.find(transactionWallet.typeId)
            it.createdAt = transactionWallet.createdAt
        }

        return walletModel
    }

    override fun getListWallet(userId: Long?): List<WalletModel> {
        if (userId == null) throw NullPointerException("User id is required.")

        val listWalletModel = arrayListOf<WalletModel>()

        val listWallet = mWalletRepository.findByUserIdOrderByUpdatedAtDesc(userId)
        if (listWallet.isNullOrEmpty()) return listWalletModel

        listWallet.forEach {
            if (it.deletedAt == null) listWalletModel.add(
                WalletModel(
                    id = it.id,
                    name = it.name,
                    type = WalletType.find(it.typeId),
                    balance = it.balance,
                    createdAt = it.createdAt
                )
            )
        }

        return listWalletModel
    }

    override fun getWallet(walletEntity: WalletEntity): WalletModel {
        val id = walletEntity.id
        val userId = walletEntity.userId
        if (id == null && userId == null) throw NullPointerException("Wallet id and user_id is required.")

        val walletModel = WalletModel()

        val wallet = mWalletRepository.findByIdAndUserIdAndDeletedAt(id ?: 0, userId ?: 0, null)
        if (wallet == null) throw NotFoundException("Wallet with provided id not found.")

        if (wallet.deletedAt != null) throw NotFoundException("Wallet with provided id not found.")

        walletModel.let {
            it.id = wallet.id
            it.name = wallet.name
            it.type = WalletType.find(wallet.typeId)
            it.balance = wallet.balance
            it.createdAt = wallet.createdAt
        }

        return walletModel
    }

}