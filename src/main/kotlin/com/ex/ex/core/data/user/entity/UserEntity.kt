package com.ex.ex.core.data.user.entity

import com.ex.ex.core.data.DataConstant
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = DataConstant.TABLE_NAME_USERS)
data class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "full_name")
    val fullName: String? = null,

    @Column(name = "email")
    val email: String? = null,

    @Column(name = "email_verified_at")
    val emailVerifiedAt: Long? = null,

    @Column(name = "password")
    val password: String? = null,

    @Column(name = "created_at")
    val createdAt: Long? = null,

    @Column(name = "updated_at")
    val updatedAt: Long? = null,

    @Column(name = "suspended_at")
    val suspendedAt: Long? = null,

    @Column(name = "deleted_at")
    val deletedAt: Long? = null

)