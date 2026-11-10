package com.example.funpay;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.funpay.dto.UserResponseDTO;
import com.example.funpay.dto.UserLoginDTO;
import com.example.funpay.dto.UserLoginResponseDTO;

import jakarta.validation.Valid;

 record User(
  String name,
  int age,
  String gender
){};
@RestController
@RequestMapping("/api/v1/users")
public class HomeController {
  private final UserRepository userRepository;
  private UserService userService;
  public HomeController(UserService userService, UserRepository userRepository){
    this.userService = userService;
    this.userRepository = userRepository;
  }
  // get all users
  @GetMapping
  public  ResponseEntity<List<UserResponseDTO>> index(){  
    List<UserResponseDTO> users = userService.getAllUsers();
    System.out.println(users);
    return  new ResponseEntity<List<UserResponseDTO>>(users,HttpStatus.OK);
  }
  // create user
  @PostMapping
  public  ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserResponseDTO params){
    return ResponseEntity.ok(userService.createUser(params));
  }

  // get specific user

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
  // because it autowires userservice and not userRepository
  UserResponseDTO user = userService.getUserById(id);
  return new ResponseEntity<>(user, HttpStatus.OK); 
  }
  // update user
  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserResponseDTO request){
    return ResponseEntity.ok(userService.updateUser(id, request));
  }

  // delete the user
  @DeleteMapping("/{id}")
  public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Long id){
    UserResponseDTO userResponseDTO = userService.deleteUser(id);
    return new ResponseEntity<>(userResponseDTO, HttpStatus.NO_CONTENT);
  }

  // login user

  @PostMapping("/login")
  public ResponseEntity<UserLoginResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO){

   UserLoginResponseDTO UserLoginResponseDTO = userService.loginUser(userLoginDTO);
   return new ResponseEntity<>(UserLoginResponseDTO, HttpStatus.ACCEPTED);
    
  }
  
}
