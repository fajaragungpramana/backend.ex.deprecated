package com.ex.ex.core.data.transaction

import com.ex.ex.core.data.transaction.model.TransactionCategory
import com.ex.ex.core.data.transaction.model.TransactionModel
import com.ex.ex.core.data.transaction.model.TransactionStatus
import com.ex.ex.core.data.transaction.model.TransactionType
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

}