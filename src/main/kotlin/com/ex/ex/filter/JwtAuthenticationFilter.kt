package com.ex.ex.filter

import com.ex.ex.constant.HttpRoute
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (HttpRoute.listPermitUrl.contains(request.servletPath)) {
            filterChain.doFilter(request, response)

            return
        }
    }

}