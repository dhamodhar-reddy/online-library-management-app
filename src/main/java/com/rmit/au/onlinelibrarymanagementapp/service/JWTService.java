package com.rmit.au.onlinelibrarymanagementapp.service;

import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidJWTException;
import com.rmit.au.onlinelibrarymanagementapp.model.User;
import com.rmit.au.onlinelibrarymanagementapp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${spring.jwt.secret.key}")
    public String secret;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(User user) throws InvalidJWTException {
        Map<String, Object> claims = new HashMap<>();
        var key = encrypt(user.getEmail()) + user.getRole();
        return createToken(claims, key);
    }

    private String createToken(Map<String, Object> claims, String key) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(key)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 300))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String validateToken(Map<String, String> headers) throws InvalidJWTException {
        Boolean validToken = Boolean.FALSE;
        String role = null;
        String authHeader = headers.get("authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ") && !isTokenExpired(authHeader.substring(7))) {
            var extractedToken = extractUsername(authHeader.substring(7));
            String email = null;
            if (extractedToken.contains("ADMIN")) {
                email = decrypt(extractedToken.substring(0, extractedToken.length() - 5));
                role = extractedToken.substring(extractedToken.length() - 5);
            } else {
                email = decrypt(extractedToken.substring(0, extractedToken.length() - 4));
                role = extractedToken.substring(extractedToken.length() - 4);
            }
            var user = userRepository.findUserByEmail(email);
            if (user.isPresent() && role.equalsIgnoreCase(user.get().getRole())) {
                validToken = Boolean.TRUE;
            }
        }
        if (!validToken) {
            throw new InvalidJWTException();
        }
        return role;
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

    public static String encrypt(String strToEncrypt) throws InvalidJWTException {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(getSecretKeyByteArray(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new InvalidJWTException();
        }
    }

    public static String decrypt(String strToDecrypt) throws InvalidJWTException {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(getSecretKeyByteArray(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decodedBytes = Base64.getDecoder().decode(strToDecrypt);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new InvalidJWTException();
        }
    }

    private static byte[] getSecretKeyByteArray() {
        return new byte[]{
                (byte) 0x4B, (byte) 0x65, (byte) 0x79, (byte) 0x30,
                (byte) 0x70, (byte) 0x5F, (byte) 0x74, (byte) 0x68,
                (byte) 0x69, (byte) 0x73, (byte) 0x5F, (byte) 0x69,
                (byte) 0x73, (byte) 0x5F, (byte) 0x61, (byte) 0x5F
        };
    }
}
