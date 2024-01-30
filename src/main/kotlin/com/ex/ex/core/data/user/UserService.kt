package com.ex.ex.core.data.user

import com.ex.ex.core.data.user.entity.UserEntity
import com.ex.ex.core.data.user.model.UserModel
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun login(userEntity: UserEntity): UserModel

    fun register(userEntity: UserEntity): UserModel

    fun user(userEntity: UserEntity): UserModel

}