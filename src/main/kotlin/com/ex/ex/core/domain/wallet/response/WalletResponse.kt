package com.ex.ex.core.domain.wallet.response

import com.fasterxml.jackson.annotation.JsonProperty

data class WalletResponse(

    @JsonProperty("id")
    val id: Long? = null,

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("type")
    val type: String? = null,

    @JsonProperty("balance")
    val balance: Long? = null,

    @JsonProperty("created_at")
    val createdAt: Long? = null

)