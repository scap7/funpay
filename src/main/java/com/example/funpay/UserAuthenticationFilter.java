package com.example.funpay;
import java.util.Map;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserAuthenticationFilter extends OncePerRequestFilter{
  @Autowired 
  private JwtUtility jwtUtility;

  UserAuthenticationFilter(JwtUtility jwtUtility){
    this.jwtUtility = jwtUtility;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

        String token = request.getHeader("x-api-key");
        Map<String, Object> payloadMap = jwtUtility.decode(token);
        System.out.println(payloadMap);
        System.out.println("------------------------------------------------------------------------------------####################################################################################3");
        // String userId = (String)payloadMap.get("userId");

        

        filterChain.doFilter(request, response);
  }

  protected boolean shouldNotFilter(HttpServletRequest request){
    String path = request.getRequestURI();
    return path.equals("/api/v1/users/login");
  }
  
}
