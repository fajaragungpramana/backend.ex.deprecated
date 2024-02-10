package com.ex.ex.controller

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.constant.HttpRoute
import com.ex.ex.core.domain.category.CategoryUseCase
import com.ex.ex.core.domain.category.response.CategoryResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(HttpRoute.V_1 + HttpRoute.CATEGORY)
@RequiredArgsConstructor
class CategoryController(private val mCategoryUseCase: CategoryUseCase) {

    @GetMapping(HttpRoute.LIST)
    fun getListCategory(): ResponseEntity<ApplicationResponse<List<CategoryResponse>>> =
        mCategoryUseCase.getListCategory()

}