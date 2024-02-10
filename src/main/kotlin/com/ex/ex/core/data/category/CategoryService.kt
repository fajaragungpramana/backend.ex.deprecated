package com.ex.ex.core.data.category

import com.ex.ex.core.data.category.model.CategoryModel

interface CategoryService {

    fun listCategory(): List<CategoryModel>

}