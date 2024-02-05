package com.ex.ex.core.data.type

import com.ex.ex.core.data.type.model.TypeCategory
import com.ex.ex.core.data.type.model.TypeModel

interface TypeService {

    fun getListType(typeCategory: TypeCategory): List<TypeModel>

}