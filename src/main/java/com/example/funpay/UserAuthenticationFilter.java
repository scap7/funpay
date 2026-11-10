package com.example.funpay;
import java.util.Map;
import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserAuthenticationFilter extends OncePerRequestFilter{

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
        String token = request.getHeader("x-api-key");
        JwtUtility jwtService = new JwtUtility();
        Map<String, Object> payloadMap = jwtService.decode(token);
        String userId = (String)payloadMap.get("userId");
        
  }
  
}
