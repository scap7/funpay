package com.example.funpay.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UserCreateRequestDTO{
  @Min(message= "should be at least 18.", value=18)
  private int userAge;
  @NotBlank(message = "Gender is required and cannot be blank")
  private String userGender;
  @NotBlank(message = "Name is required and cannot be blank")
  private String userName;
  @NotBlank(message = "password cannot be blank")
  private String password;
  @NotBlank(message = "password confirmation cannot be blank")
  private String passwordConfirmation;
  @NotBlank
  private String email;

  // getters
  public int getUserAge(){
    return this.userAge;
  }


  public String getUserGender(){
    return this.userGender;
  }
  public String getUserName(){  
    return this.userName;
  }

  public String getPassword(){
    return this.password;
  }

  public String getPasswordConfirmation(){
    return this.passwordConfirmation;
  }

  public String getEmail(){
    return this.email;
  }

  // setters
  public void setUserAge(int age){
    this.userAge = age;
  }

  public void setUserGender(String gender){
    this.userGender = gender;
  }

  public void setUserName(String userName){
    this.userName = userName;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public void setPasswordConfirmation(String password){
    this.passwordConfirmation = password;
  }

  public String toString(){
    return "UserCreateRequestDTO: [ userAge: " + userAge + " userName: " + userName + ", userEmail: " + email;
  }

}