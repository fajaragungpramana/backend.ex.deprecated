package com.ex.ex.core.domain.authorization.response

import com.ex.ex.core.data.jwt.model.JwtType

data class AuthorizationResponse(
    var userId: Long? = null,
    var isJwtGranted: Boolean? = null,
    var isJwtValid: Boolean? = null,
    var isJwtExpired: Boolean? = null,
    var jwtType: JwtType? = null
)