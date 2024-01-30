package com.ex.ex.core.domain.authentication.response

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponse(

    @JsonProperty("access_token")
    val accessToken: String? = null,

    @JsonProperty("refresh_token")
    val refreshToken: String? = null

)