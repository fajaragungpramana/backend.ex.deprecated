package com.ex.ex.filter

import com.ex.ex.constant.HttpHeader
import com.ex.ex.constant.HttpRoute
import com.ex.ex.core.domain.authorization.AuthorizationUseCase
import com.ex.ex.extension.setMessage
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter(
    private val mAuthorizationUseCase: AuthorizationUseCase
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (HttpRoute.listPermitUrl.contains(request.servletPath)) {
            filterChain.doFilter(request, response)

            return
        }

        val authorizationHeader = request.getHeader(HttpHeader.AUTHORIZATION)
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setMessage("Authorization is required.", HttpStatus.UNAUTHORIZED)

            return
        }

        val token = authorizationHeader.substring(7)
        val authorization = mAuthorizationUseCase.getToken(token)

        if (authorization.isJwtValid == false || authorization.isJwtGranted == false) {
            response.setMessage("Authorization token invalid.", HttpStatus.UNAUTHORIZED)

            return
        }

        if (authorization.isJwtExpired == true) {
            response.setMessage("Authorization token expired.", HttpStatus.UNAUTHORIZED)

            return
        }

        if (authorization.userId == null || SecurityContextHolder.getContext().authentication == null) {
            val user = mAuthorizationUseCase.getUser(authorization.userId)
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        }

        filterChain.doFilter(request, response)
    }

}