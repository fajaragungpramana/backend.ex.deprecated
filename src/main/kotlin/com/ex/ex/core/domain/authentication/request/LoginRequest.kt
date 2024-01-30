package com.ex.ex.core.domain.authentication.request

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(

    @JsonProperty("email")
    val email: String? = null,

    @JsonProperty("password")
    val password: String? = null

)