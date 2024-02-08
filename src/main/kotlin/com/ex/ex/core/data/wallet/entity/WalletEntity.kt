package com.ex.ex.core.data.wallet.entity

import com.ex.ex.core.data.DataConstant
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = DataConstant.TABLE_NAME_WALLETS)
data class WalletEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: Long? = null,

    @Column(name = "name")
    val name: String? = null,

    @Column(name = "balance")
    val balance: Long? = null,

    @Column(name = "type_id")
    val typeId: Long? = null,

    @Column(name = "created_at")
    val createdAt: Long? = null,

    @Column(name = "updated_at")
    val updatedAt: Long? = null,

    @Column(name = "deleted_at")
    val deletedAt: Long? = null

)