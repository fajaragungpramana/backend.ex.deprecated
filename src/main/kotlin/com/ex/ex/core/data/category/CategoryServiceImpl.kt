package com.ex.ex.core.data.category

import com.ex.ex.core.data.category.model.CategoryModel
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class CategoryServiceImpl(private val mCategoryRepository: CategoryRepository) : CategoryService {

    override fun listCategory(): List<CategoryModel> {
        val listCategoryModel = arrayListOf<CategoryModel>()

        mCategoryRepository.findAll().forEach {
            if (it.deletedAt == null)
                listCategoryModel.add(
                    CategoryModel(
                        id = it.id,
                        name = it.name
                    )
                )
        }

        return listCategoryModel
    }

}