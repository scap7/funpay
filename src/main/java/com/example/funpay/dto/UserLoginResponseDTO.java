package com.example.funpay.dto;


public class UserLoginResponseDTO {
 private String token;

 public UserLoginResponseDTO(String token){
  this.token = token;
 }

 public void setToken(String token){
  this.token = token;
 }

 public String getToken(){
  return this.token;
 } 

}
