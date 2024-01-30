package com.ex.ex.extension

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.SecureRandom
import java.util.regex.Pattern

fun String.isEmail() = Pattern.compile("^(.+)@(.+)\$").matcher(this).matches()

fun String.bcryptEncode(): String = BCryptPasswordEncoder(10, SecureRandom()).encode(this)

fun String.bcryptMatches(encoded: String) = BCryptPasswordEncoder(10, SecureRandom()).matches(this, encoded)