package com.ex.ex.core.data.transaction

import com.ex.ex.core.data.transaction.entity.TransactionEntity
import com.ex.ex.core.data.transaction.model.TransactionCategory
import com.ex.ex.core.data.transaction.model.TransactionModel
import com.ex.ex.core.data.transaction.model.TransactionStatus
import com.ex.ex.core.data.transaction.model.TransactionType
import com.ex.ex.core.exception.ForbiddenException
import com.ex.ex.core.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class TransactionServiceImpl(private val mTransactionRepository: TransactionRepository) : TransactionService {

    override fun getListTransactionByListId(listTransactionId: List<Long>): List<TransactionModel> {
        if (listTransactionId.isEmpty()) throw NullPointerException("List transaction is required.")

        val listTransactionModel = arrayListOf<TransactionModel>()
        val listTransaction = mTransactionRepository.findByIdInAndDeletedAt(listTransactionId, null)
        listTransaction?.forEach {
            if (it.deletedAt == null)
                listTransactionModel.add(
                    TransactionModel(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        category = TransactionCategory.find(it.categoryId),
                        status = TransactionStatus.find(it.statusId),
                        type = TransactionType.find(it.typeId),
                        createdAt = it.createdAt
                    )
                )
        }

        return listTransactionModel
    }

    override fun setTransaction(transactionEntity: TransactionEntity): TransactionModel {
        val transactionModel = TransactionModel()

        val transaction = mTransactionRepository.save(transactionEntity)
        transactionModel.let {
            it.id = transaction.id
            it.name = transaction.name
            it.description = transaction.description
            it.status = TransactionStatus.find(transaction.statusId)
            it.type = TransactionType.find(transaction.typeId)
            it.category = TransactionCategory.find(transaction.categoryId)
            it.createdAt = transaction.createdAt
        }

        return transactionModel
    }

    override fun setTransactionStatus(transactionId: Long?, transactionStatus: TransactionStatus): TransactionModel {
        if (transactionId == null) throw NullPointerException("Transaction id is required.")

        val transactionModel = TransactionModel()

        val transactionQuery = mTransactionRepository.findById(transactionId)
        if (transactionQuery.isEmpty) throw NotFoundException("Transaction with provided id not found.")

        val transaction = transactionQuery.get()
        if (transaction.deletedAt != null) throw ForbiddenException("Can't update status because transaction has been deleted.")

        val updatedTransaction = mTransactionRepository.save(transaction.copy(statusId = transactionStatus.id))
        transactionModel.let {
            it.id = updatedTransaction.id
            it.name = updatedTransaction.name
            it.description = updatedTransaction.description
            it.status = TransactionStatus.find(updatedTransaction.statusId)
            it.type = TransactionType.find(updatedTransaction.typeId)
            it.category = TransactionCategory.find(updatedTransaction.categoryId)
            it.createdAt = updatedTransaction.createdAt
        }

        return transactionModel
    }

}