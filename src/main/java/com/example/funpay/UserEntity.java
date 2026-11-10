package com.example.funpay;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class UserEntity{

  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  
  @Column(nullable = false)
  private String gender;
  @Column(nullable = false)
  private int age;
  private String email;
  private String password;

  public UserEntity(){

  }

  public UserEntity(String name, String gender, int age){
    this.name = name;
    this.gender = gender;
    this.age = age;
  }

  // Add these Getters so Spring can convert the object TO a JSON response
  public Long getId() { return id; }
  public String getName() { return name; }
  public String getGender() { return gender; }
  public int getAge() { return age; }
  public String getEmail(){return email;}
  public String getPassword(){return password;}

  // Add these Setters so Spring can convert the incoming JSON RequestBody INTO the object
  public void setId(Long id) { this.id = id; }
  public void setName(String name) { this.name = name; }
  public void setGender(String gender) { this.gender = gender; }
  public void setAge(int age) { this.age = age; }
  public void setEmail(String email){this.email = email;}
  public void setPassword(String pass){this.password = pass;}

  public String toString(){
    return "User:[" + "|name:" + this.name + "| age: " + this.age + "| gender: " + this.gender + "]";
  }
}
