package com.ex.ex.core.domain.type

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.data.type.model.TypeCategory
import com.ex.ex.core.domain.type.response.TypeResponse
import org.springframework.http.ResponseEntity

interface TypeUseCase {

    fun getListType(typeCategory: TypeCategory): ResponseEntity<ApplicationResponse<List<TypeResponse>>>

}