package com.example.onlinebanking.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mysupersecretkey12345";

    // extract username (email)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // extract expiration
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // get all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // check expiration
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // generate token (accepts username string)
    public String generateToken(String username) {
        return createToken(username);
    }

    // optional overload if you ever want to pass UserDetails directly
    // -------------

    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    private String createToken(String username) {

        return Jwts.builder()
                .setSubject(username)                     // store email inside token
                .setIssuedAt(new Date())                  // token created at
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))  // 24 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
