package com.rmit.au.onlinelibrarymanagementapp.service;

import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidJWTException;
import com.rmit.au.onlinelibrarymanagementapp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${spring.jwt.secret.key}")
    private String SECRET;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void validateToken(Map<String, String> headers) throws InvalidJWTException {
        Boolean validToken = Boolean.FALSE;
        String authHeader = headers.get("authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ") && !isTokenExpired(authHeader.substring(7))) {
            var email = extractUsername(authHeader.substring(7));
            var user = userRepository.findUserByEmail(email);
            if (user.isPresent()) {
                validToken = Boolean.TRUE;
            }
        }
        if (!validToken) {
            throw new InvalidJWTException();
        }
    }

    private String extractUsername(String token) throws InvalidJWTException {
        return extractClaim(token, Claims::getSubject);
    }

    private Boolean isTokenExpired(String token) throws InvalidJWTException {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) throws InvalidJWTException {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception exception) {
            throw new InvalidJWTException();
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws InvalidJWTException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws InvalidJWTException {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception exception) {
            throw new InvalidJWTException();
        }
    }
}
