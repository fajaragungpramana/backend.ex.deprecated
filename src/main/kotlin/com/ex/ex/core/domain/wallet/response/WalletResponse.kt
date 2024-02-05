package com.ex.ex.core.domain.wallet.response

import com.fasterxml.jackson.annotation.JsonProperty

data class WalletResponse(

    @JsonProperty("name")
    val name: String? = null

)