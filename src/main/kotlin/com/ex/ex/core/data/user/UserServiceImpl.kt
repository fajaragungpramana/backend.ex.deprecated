package com.ex.ex.core.data.user

import com.ex.ex.core.data.user.entity.UserEntity
import com.ex.ex.core.data.user.model.UserModel
import com.ex.ex.core.exception.ForbiddenException
import com.ex.ex.core.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserServiceImpl(private val mUserRepository: UserRepository) : UserService {

    override fun login(userEntity: UserEntity): UserModel {
        if (userEntity.email == null) throw NullPointerException("User email is required.")

        val userModel = UserModel()

        val userQuery = mUserRepository.findByEmail(userEntity.email) ?: throw NotFoundException("User with provided email not found.")
        when {
            userQuery.suspendedAt != null -> throw ForbiddenException("User is suspended.")
            userQuery.deletedAt != null -> throw ForbiddenException("User is deleted.")
        }

        userModel.id = userQuery.id

        return userModel
    }

}