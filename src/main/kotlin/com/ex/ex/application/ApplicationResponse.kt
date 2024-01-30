package com.ex.ex.application

import com.fasterxml.jackson.annotation.JsonProperty

data class ApplicationResponse<T>(

    @JsonProperty("message")
    var message: String? = null,

    @JsonProperty("data")
    var data: T? = null

)