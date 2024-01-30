package com.ex.ex.configuration

import com.ex.ex.constant.HttpRoute
import com.ex.ex.extension.setMessage
import com.ex.ex.filter.JwtAuthenticationFilter
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
class SecurityConfiguration(private val mJwtAuthenticationFilter: JwtAuthenticationFilter) {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        HttpRoute.V_1 + HttpRoute.AUTHENTICATION + HttpRoute.LOGIN
                    ).permitAll()
                    .anyRequest().authenticated()

            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, authException ->
                    response.setMessage(authException.localizedMessage, HttpStatus.UNAUTHORIZED)
                }
            }
            .addFilterBefore(mJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return httpSecurity.build()
    }

}