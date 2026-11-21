package com.example.funpay.Exceptions;

public class UserNotAuthenticatedException extends RuntimeException{
  public UserNotAuthenticatedException(String message){
    super(message);
  }
}
