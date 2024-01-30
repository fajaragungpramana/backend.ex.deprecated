package com.ex.ex.core.data.jwt.entity

import com.ex.ex.core.data.jwt.model.JwtType

data class JwtEntity(
    var userId: Long? = null,
    var jwtType: JwtType? = null
) {

    fun map() = hashMapOf<String, String?>(
        USER_ID to userId.toString(),
        JWT_TYPE to jwtType?.name.toString()
    )
    companion object {
        const val USER_ID = "user_id"
        const val JWT_TYPE = "jwt_type"
    }

}