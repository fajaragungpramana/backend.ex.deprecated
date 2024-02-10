package com.ex.ex.core.domain.category

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.domain.category.response.CategoryResponse
import org.springframework.http.ResponseEntity

interface CategoryUseCase {

    fun getListCategory(): ResponseEntity<ApplicationResponse<List<CategoryResponse>>>

}