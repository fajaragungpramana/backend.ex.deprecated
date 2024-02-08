package com.ex.ex.core.domain.type

import com.ex.ex.application.ApplicationResponse
import com.ex.ex.core.data.type.TypeService
import com.ex.ex.core.data.type.model.TypeCategory
import com.ex.ex.core.domain.type.response.TypeResponse
import com.ex.ex.core.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class TypeInteractor(private val mTypeService: TypeService) : TypeUseCase {

    override fun getListType(typeCategory: TypeCategory): ResponseEntity<ApplicationResponse<List<TypeResponse>>> {
        val responseBody = ApplicationResponse<List<TypeResponse>>()
        try {
            val listTypeResponse = arrayListOf<TypeResponse>()

            val listTypeModel = mTypeService.getListType(typeCategory)
            listTypeModel.forEach {
                listTypeResponse.add(
                    TypeResponse(
                        id = it.id,
                        name = it.name
                    )
                )
            }

            responseBody.message = "Type found."
            responseBody.data = listTypeResponse

        } catch (e: Exception) {
            responseBody.message = e.message

            return ResponseEntity.status(
                when (e) {
                    is NotFoundException -> HttpStatus.NOT_FOUND

                    else -> HttpStatus.INTERNAL_SERVER_ERROR
                }
            ).body(responseBody)
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody)
    }

}