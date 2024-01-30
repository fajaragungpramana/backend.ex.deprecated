package com.ex.ex.core.data.jwt

import com.ex.ex.core.data.jwt.entity.JwtEntity
import com.ex.ex.core.data.jwt.model.JwtModel

interface JwtService {

    fun setToken(jwtEntity: JwtEntity): JwtModel

}