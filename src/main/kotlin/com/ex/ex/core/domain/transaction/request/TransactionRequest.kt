package com.ex.ex.core.domain.transaction.request

import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionRequest(

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("wallet_id")
    val walletId: Long? = null,

    @JsonProperty("amount")
    val amount: Long? = null,

    @JsonProperty("category_id")
    val categoryId: Long? = null

)