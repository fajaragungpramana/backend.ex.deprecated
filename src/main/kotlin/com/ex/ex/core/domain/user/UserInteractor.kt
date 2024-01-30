package com.ex.ex.core.domain.user

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.data.user.UserService
import com.ex.ex.core.data.user.entity.UserEntity
import com.ex.ex.core.domain.user.response.UserResponse
import com.ex.ex.core.exception.ForbiddenException
import com.ex.ex.core.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class UserInteractor(private val mUserService: UserService) : UserUseCase {

    override fun onUser(id: Long?): ResponseEntity<ApplicationResponse<UserResponse>> {
        val responseBody = ApplicationResponse<UserResponse>()

        try {
            val user = mUserService.user(UserEntity(id = id))

            responseBody.message = "User found."
            responseBody.data = UserResponse(
                fullName = user.fullName,
                email = user.email
            )
        } catch (e: Exception) {
            responseBody.message = e.message

            return ResponseEntity.status(
                when (e) {
                    is NotFoundException -> HttpStatus.NOT_FOUND
                    is ForbiddenException -> HttpStatus.FORBIDDEN

                    else -> HttpStatus.INTERNAL_SERVER_ERROR
                }
            ).body(responseBody)
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody)
    }

}