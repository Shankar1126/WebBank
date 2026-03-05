/**
 * File-level: Utility class for creating and validating JWTs using jjwt.
 *
 * Note: Ensure io.jsonwebtoken (jjwt) dependencies are on the classpath.
 */

package com.spring.ex.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JwtUtil provides helpers to generate and validate JWT tokens.
 *
 * - generateToken(...) creates a signed token using HS256.
 * - validateToken(...) parses and validates a token and returns Jws<Claims>.
 */
@Component
public class JwtUtil {

    /**
     * Secret key used for signing JWTs.
     */
    private final SecretKey key;

    /**
     * Token expiration in milliseconds.
     */
    private final long expirationMs;

    /**
     * Constructor reads jwt.secret and jwt.expiration-ms from application properties.
     *
     * @param secret       signing secret (must be sufficiently long for HS256)
     * @param expirationMs expiration time in milliseconds
     */
    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration-ms:3600000}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Generate a signed JWT for a given username and role.
     *
     * @param username subject (usually username)
     * @param role     custom claim "role"
     * @return compact JWS string
     */
    public String generateToken(String username, String role) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)               // use setSubject, not subject()
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate and parse a JWT string.
     *
     * @param token compact JWS string
     * @return parsed Jws<Claims>
     * @throws JwtException if token is invalid/expired
     */
    public Jws<Claims> validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}