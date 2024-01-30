package com.ex.ex.core.domain.user.response

import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(

    @JsonProperty("full_name")
    val fullName: String? = null,

    @JsonProperty("email")
    val email: String? = null

)