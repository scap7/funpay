package com.example.funpay;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration 
public class FilterConfig {
  @Bean 
  public FilterRegistrationBean<UserAuthenticationFilter> jwtFilter(JwtUtility jwtUtility){
    FilterRegistrationBean<UserAuthenticationFilter> registerFilter = new FilterRegistrationBean<>();
    registerFilter.setFilter(new UserAuthenticationFilter(jwtUtility));
    registerFilter.addUrlPatterns("/api/v1/users/*");
    registerFilter.setOrder(1);
    return registerFilter;
  }
}
