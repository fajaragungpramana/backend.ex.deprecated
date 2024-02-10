package com.ex.ex.core.domain.category.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryResponse(

    @JsonProperty("id")
    val id: Long? = null,

    @JsonProperty("name")
    val name: String? = null

)