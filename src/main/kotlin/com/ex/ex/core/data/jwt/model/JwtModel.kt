package com.ex.ex.core.data.jwt.model

data class JwtModel(
    var accessToken: String? = null,
    var refreshToken: String? = null,
    var userId: String? = null,
    var jwtType: JwtType? = null,
    var isInvalid: Boolean? = null,
    var isExpired: Boolean? = null
)