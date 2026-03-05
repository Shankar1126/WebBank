package com.spring.ex.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * File-level: Servlet filter that validates JWT on each request and sets Spring Security Authentication.
 *
 * This filter expects a JwtUtil in the same package that provides a validateToken(String) method
 * returning io.jsonwebtoken.Jws<io.jsonwebtoken.Claims>.
 */

/**
 * JwtAuthenticationFilter extracts the Bearer token from the Authorization header,
 * validates it using JwtUtil and, if valid, populates the SecurityContext with an
 * authenticated UsernamePasswordAuthenticationToken containing a role authority.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * JwtUtil used to validate and parse JWT tokens.
     */
    private final JwtUtil jwtUtil;

    /**
     * Constructor for the filter.
     *
     * @param jwtUtil utility responsible for token parsing/validation
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Filter logic: extract Bearer token, validate it and populate SecurityContext.
     *
     * If token is invalid or absent, the filter clears the security context and continues;
     * Spring Security will then handle authorization decisions (e.g., reject requests to protected endpoints).
     *
     * @param request     incoming HTTP request
     * @param response    HTTP response
     * @param filterChain filter chain
     * @throws ServletException on servlet errors
     * @throws IOException      on IO errors
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Jws<Claims> claims = jwtUtil.validateToken(token);
                String username = claims.getBody().getSubject();
                String role = (String) claims.getBody().get("role");
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex) {
                // Invalid token -> clear context and continue (request will be rejected by security)
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}
