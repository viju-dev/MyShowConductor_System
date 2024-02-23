package com.example.MyShowConductor_System.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {
    // JWT token validity duration in seconds
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // Secret key used for token signing
    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            // Handle exception during token parsing
            throw new RuntimeException("Error parsing token: " + e.getMessage());
        }
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }


    private String doGenerateToken(Map<String, Object> claims, String subject) {

        try {
            return Jwts.builder().setClaims(claims).setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                    .signWith(getSecretKey(), SignatureAlgorithm.HS512).compact();
        } catch (Exception e) {
            // Handle exception during token generation
            throw new RuntimeException("Error generating token: " + e.getMessage());
        }
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        System.out.println(token);
        final String username = getUsernameFromToken(token);
        System.out.println(username);
        System.out.println(userDetails.getUsername());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Get the secret key from the provided secret string
    private SecretKey getSecretKey() {
        try {
            byte[] decodedSecret = secret.getBytes(StandardCharsets.UTF_8);
            return new SecretKeySpec(decodedSecret, SignatureAlgorithm.HS512.getJcaName());
        } catch (Exception e) {
            // Handle exception during secret key retrieval
            throw new RuntimeException("Error retrieving secret key: " + e.getMessage());
        }
    }
}
