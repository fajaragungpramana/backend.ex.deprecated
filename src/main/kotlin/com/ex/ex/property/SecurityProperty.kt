package com.ex.ex.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = SecurityProperty.NAME)
data class SecurityProperty(
    var jwtPassword: String? = null
) {

    companion object {
        const val NAME = "security"
    }

}