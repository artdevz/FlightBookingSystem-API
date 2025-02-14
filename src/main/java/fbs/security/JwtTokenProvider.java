package fbs.security;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import fbs.configs.JwtConfig;
import fbs.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {
    
    private static final String SECRET_KEY = JwtConfig.getSecretKey();
    private static final long EXPIRATION_TIME_MS = JwtConfig.getExpirationTime();

    public String generateToken(Authentication auth) {

        User userPrincipal = (User) auth.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME_MS);
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", userPrincipal.getId());

        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key)
            .compact();

    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                .setSigningKey(new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                .build()
                .parseClaimsJws(token);
            return true;
        }

        catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }

    public String resolveToken(HttpServletRequest request) {
        
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) return bearerToken.substring(7);
        return null;

    }

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        User userDetails = new User();
        userDetails.setEmail(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", new ArrayList<>());
    
    }

}
