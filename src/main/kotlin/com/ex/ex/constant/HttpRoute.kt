package com.ex.ex.constant

object HttpRoute {

    // VERSION
    const val V_1 = "/v1"

    // ROOT
    const val AUTHENTICATION = "/authentication"

    // END-POINT
    const val LOGIN = "/login"

    const val REGISTER = "/register"

    val listPermitUrl = arrayOf(
        V_1 + AUTHENTICATION + LOGIN,
        V_1 + AUTHENTICATION + REGISTER
    )

}