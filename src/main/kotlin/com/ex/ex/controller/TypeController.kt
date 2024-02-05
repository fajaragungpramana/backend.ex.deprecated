package com.ex.ex.controller

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.constant.HttpRoute
import com.ex.ex.core.data.type.model.TypeCategory
import com.ex.ex.core.domain.type.TypeUseCase
import com.ex.ex.core.domain.type.response.TypeResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(HttpRoute.V_1 + HttpRoute.TYPE)
@RequiredArgsConstructor
class TypeController(private val mTypeUseCase: TypeUseCase) {

    @GetMapping(HttpRoute.WALLET)
    fun getListTypeWallet(): ResponseEntity<ApplicationResponse<List<TypeResponse>>> =
        mTypeUseCase.getListType(TypeCategory.WALLET)

}