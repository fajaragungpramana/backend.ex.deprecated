package com.ex.ex.core.data.category

import com.ex.ex.core.data.category.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<CategoryEntity, Long>