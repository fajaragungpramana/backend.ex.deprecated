package com.ex.ex.core.data.type

import com.ex.ex.core.data.type.entity.TypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TypeRepository : JpaRepository<TypeEntity, Long> {

    fun findByCategory(category: String): List<TypeEntity>?

}