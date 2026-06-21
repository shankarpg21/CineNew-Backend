package com.example.Cinenew_backend.config;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.Cinenew_backend.enumData.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private static final long EXPIRATION_TIME=1000*60*60;

    private SecretKey signingKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private Claims getClaims(String token){
        return Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims(token).getPayload();
    }

    public String generateToken(Long userId,Role role){
        Map<String,Object> claims=new HashMap<>();
        claims.put("role", role.name());
        return Jwts.builder().claims(claims).subject(String.valueOf(userId)).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME)).signWith(signingKey()).compact();
    }

    public Long extractUserId(String token){
        Claims claims=getClaims(token);
        return Long.valueOf(claims.getSubject());
    }

    public String extractRole(String token){
        Claims claims=getClaims(token);
        return claims.get("role",String.class);
    }

    public boolean isValidToken(String token){
        Claims claims=getClaims(token);
        Date tokenTime=claims.getExpiration();
        Date currTime=new Date(System.currentTimeMillis());
        return currTime.before(tokenTime);
    }

}
