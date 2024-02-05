package com.ex.ex.core.domain.wallet.request

import com.fasterxml.jackson.annotation.JsonProperty

data class WalletRequest(

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("type_id")
    val typeId: Long? = null,

    @JsonProperty("balance")
    val balance: Long? = null

)