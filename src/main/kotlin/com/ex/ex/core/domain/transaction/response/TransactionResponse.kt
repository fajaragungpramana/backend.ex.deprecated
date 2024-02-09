package com.ex.ex.core.domain.transaction.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TransactionResponse(

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("type")
    val type: String? = null,

    @JsonProperty("category")
    val category: String? = null,

    @JsonProperty("status")
    val status: String? = null,

    @JsonProperty("payment")
    val payment: Payment? = null,

    @JsonProperty("created_at")
    val createdAt: Long? = null

) {

    data class Payment(

        @JsonProperty("amount")
        val amount: Long? = null,

        @JsonProperty("status")
        val status: String? = null,

        @JsonProperty("type")
        val type: String? = null,

        @JsonProperty("created_at")
        val createdAt: Long? = null

    )

}