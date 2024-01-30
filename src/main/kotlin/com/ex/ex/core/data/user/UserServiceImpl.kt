package com.ex.ex.core.data.user

import com.ex.ex.core.data.user.entity.UserEntity
import com.ex.ex.core.data.user.model.UserModel
import com.ex.ex.core.exception.ForbiddenException
import com.ex.ex.core.exception.NotFoundException
import com.ex.ex.extension.bcryptEncode
import com.ex.ex.extension.bcryptMatches
import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserServiceImpl(private val mUserRepository: UserRepository) : UserService {

    override fun login(userEntity: UserEntity): UserModel {
        if (userEntity.email == null) throw NullPointerException("User email is required.")

        val userModel = UserModel()

        val userQuery = mUserRepository.findByEmail(userEntity.email) ?: throw NotFoundException("User with provided email not found.")
        if (!userEntity.password.orEmpty().bcryptMatches(userQuery.password.orEmpty())) throw ForbiddenException("Invalid password.")

        when {
            userQuery.suspendedAt != null -> throw ForbiddenException("User is suspended.")
            userQuery.deletedAt != null -> throw ForbiddenException("User is deleted.")
        }

        userModel.id = userQuery.id

        return userModel
    }

    override fun register(userEntity: UserEntity): UserModel {
        if (userEntity.email == null) throw NullPointerException("User email is required.")

        val userModel = UserModel()

        val userQuery = mUserRepository.findByEmail(userEntity.email)
        if (userQuery != null) throw ForbiddenException("User with same email is already exists.")

        val encodePassword = userEntity.password.orEmpty().bcryptEncode()

        mUserRepository.save(userEntity.copy(password = encodePassword)).let {
            userModel.id = it.id
        }

        return userModel
    }

    override fun user(userEntity: UserEntity): UserModel {
        if (userEntity.id == null) throw NullPointerException("User id is required.")

        val userModel = UserModel()

        val userQuery = mUserRepository.findById(userEntity.id)
        if (userQuery.isEmpty) throw NotFoundException("User with provided id not found.")

        val user = userQuery.get()

        when {
            user.suspendedAt != null -> throw ForbiddenException("User is suspended.")
            user.deletedAt != null -> throw ForbiddenException("User is deleted.")
        }

        userModel.let {
            it.id = user.id
            it.fullName = user.fullName
            it.email = user.email
        }

        return userModel
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val userQuery = mUserRepository.findById(username.orEmpty().toLong())
        if (userQuery.isEmpty) throw UsernameNotFoundException("User with the provide id not found.")

        val user = userQuery.get()
        if (user.suspendedAt != null) throw ForbiddenException("User is suspended.")
        if (user.deletedAt != null) throw ForbiddenException("User is deleted.")

        return User.builder()
            .username(username)
            .password(user.password)
            .roles("USER")
            .authorities("USER")
            .build()
    }

}