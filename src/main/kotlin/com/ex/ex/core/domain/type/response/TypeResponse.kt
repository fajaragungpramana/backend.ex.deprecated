package com.ex.ex.core.domain.type.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TypeResponse(

    @JsonProperty("id")
    val id: Long? = null,

    @JsonProperty("name")
    val name: String? = null

)