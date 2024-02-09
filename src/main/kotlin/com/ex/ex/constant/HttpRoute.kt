package com.ex.ex.constant

object HttpRoute {

    // VERSION
    const val V_1 = "/v1"

    // ROOT
    const val AUTHENTICATION = "/authentication"
    const val USER = "/user"
    const val TYPE = "/type"
    const val TRANSACTION = "/transaction"

    // END-POINT
    const val LOGIN = "/login"
    const val REGISTER = "/register"
    const val ME = "/me"
    const val WALLET = "/wallet"
    const val CREATE = "/create"

    val listPermitUrl = arrayOf(
        V_1 + AUTHENTICATION + LOGIN,
        V_1 + AUTHENTICATION + REGISTER
    )

}