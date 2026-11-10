package com.example.funpay.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.example.funpay.UserEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonPropertyOrder({ "userId", "userName", "userAge", "userGender" })
public class UserResponseDTO {
  private Long userId;
  @Min(message= "should be at least 18.", value=18)
  private int userAge;
  @NotBlank(message = "Gender is required and cannot be blank")
  private String userGender;
  @NotBlank(message = "Name is required and cannot be blank")
  private String userName;

  public UserResponseDTO(UserEntity userEntity){
    this.userId = userEntity.getId();
    this.userAge = userEntity.getAge();
    this.userGender = userEntity.getGender();
    this.userName = userEntity.getName();
  }

  public UserResponseDTO(){

  }

  // getters
  public Long getUserId(){
    return this.userId;
  }
  public int getUserAge(){
    return this.userAge;
  }


  public String getUserGender(){
    return this.userGender;
  }
  public String getUserName(){  
    return this.userName;
  }

  // setters
  public void setUserId(Long id){
    this.userId = id;
  }

  public void setUserAge(int age){
    this.userAge = age;
  }

  public void setUserGender(String gender){
    this.userGender = gender;
  }

  public String setUserName(String userName){
    return this.userName = userName;
  }

  public String toString(){
    return "UserResponseDTO: [" + "userId: " + userId + " userAge: " + userAge + " userName: " + userName;
  }

}
