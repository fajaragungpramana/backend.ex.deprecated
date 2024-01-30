package com.ex.ex.core.data.user

import com.ex.ex.core.data.user.entity.UserEntity
import com.ex.ex.core.data.user.model.UserModel

interface UserService {

    fun login(userEntity: UserEntity): UserModel

    fun register(userEntity: UserEntity): UserModel

}