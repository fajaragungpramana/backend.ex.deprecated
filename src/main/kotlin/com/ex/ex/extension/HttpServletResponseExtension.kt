package com.ex.ex.extension

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

fun HttpServletResponse.setMessage(message: String, status: HttpStatus) {
    this.status = status.value()
    this.contentType = MediaType.APPLICATION_JSON_VALUE

    val body = mapOf(
        "message" to message,
        "data" to null
    )

    this.writer.write(
        ObjectMapper().writeValueAsString(body)
    )
}