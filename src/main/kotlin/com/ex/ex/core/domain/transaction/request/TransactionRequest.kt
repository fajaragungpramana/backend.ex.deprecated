package com.ex.ex.core.domain.transaction.request

import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionRequest(

    @JsonProperty("page")
    val page: Int = 1,

    @JsonProperty("limit")
    val limit: Int = 15

)