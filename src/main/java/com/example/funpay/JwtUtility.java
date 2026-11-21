package com.example.funpay;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import javax.crypto.SecretKey;


@Service
public class JwtUtility {
// ensure security key is base64 encoded beforehand 
  @Value("${security.jwt.secret-key}")
  private String secretKey;

  @Value("${security.jwt.expiration-time}") // in secs
  private long expirationTimeMillis;

  public String encode(String subject){
    return encode(subject, java.util.Collections.emptyMap());
  }

  // encode

  public String encode(String subject, Map<String, Object> extraClaims){
    long currentMillis = System.currentTimeMillis();
    Date now = new Date(currentMillis);
    Date expirationTime = new Date(currentMillis + expirationTimeMillis);

    String jws = Jwts.builder()
    .subject(subject)
    .expiration(expirationTime)
    .notBefore(now) //a java.util.Date
    .issuedAt(now) // for example, now
    .claims(extraClaims)
    .signWith(getSigningKey(secretKey), Jwts.SIG.HS256)
    .id(UUID.randomUUID().toString())
    .compact(); //just an example id
    return jws;
  }
  // verify validity + decode
  
  public Claims decode(String token){
      return Jwts.parser()
      .verifyWith(getSigningKey(secretKey))
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  private SecretKey getSigningKey(String secretKey){
    byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
