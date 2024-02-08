package com.ex.ex.core.data.type

import com.ex.ex.core.data.type.model.TypeCategory
import com.ex.ex.core.data.type.model.TypeModel
import com.ex.ex.core.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
data class TypeServiceImpl(private val mTypeRepository: TypeRepository) : TypeService {

    override fun getListType(typeCategory: TypeCategory): List<TypeModel> {
        val listTypeEntity = mTypeRepository.findByCategory(typeCategory.name)
        if (listTypeEntity.isNullOrEmpty()) throw NotFoundException("List type with provided category not found.")

        val listType = arrayListOf<TypeModel>()
        listTypeEntity.forEach {
            if (it.deletedAt == null)
                listType.add(
                    TypeModel(
                        id = it.id,
                        name = it.name
                    )
                )
        }

        return listType
    }

}