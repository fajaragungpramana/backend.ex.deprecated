package com.ex.ex.core.domain.category

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.data.category.CategoryService
import com.ex.ex.core.domain.category.response.CategoryResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class CategoryInteractor(private val mCategoryService: CategoryService) : CategoryUseCase {

    override fun getListCategory(): ResponseEntity<ApplicationResponse<List<CategoryResponse>>> {
        val responseBody = ApplicationResponse<List<CategoryResponse>>()

        try {
            val listCategory = arrayListOf<CategoryResponse>()

            mCategoryService.listCategory().forEach {
                listCategory.add(
                    CategoryResponse(
                        id = it.id,
                        name = it.name
                    )
                )
            }

            responseBody.message = "Categories found."
            responseBody.data = listCategory

        } catch (e: Exception) {
            responseBody.message = e.message

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody)
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody)
    }

}