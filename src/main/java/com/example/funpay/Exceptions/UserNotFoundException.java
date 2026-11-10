package com.example.funpay.Exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message){
    super(message);
  }
}
