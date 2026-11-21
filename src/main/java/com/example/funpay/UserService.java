package com.example.funpay;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.funpay.Exceptions.UserNotFoundException;
import com.example.funpay.Exceptions.UserNotAuthenticatedException;
import com.example.funpay.dto.UserCreateRequestDTO;
import com.example.funpay.dto.UserLoginDTO;
import com.example.funpay.dto.UserLoginResponseDTO;
import com.example.funpay.dto.UserResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service 
public class UserService {
  private UserRepository userRepository;
  private BCryptPasswordEncoder passwordEncoder;
  private JwtUtility jwtUtility;

  public UserService(UserRepository userRepository, JwtUtility jwtUtility){
    this.userRepository = userRepository;
    this.passwordEncoder  = new BCryptPasswordEncoder();
    this.jwtUtility = jwtUtility;
  }

  public List<UserResponseDTO> getAllUsers(){
    List<UserEntity> users = userRepository.findAll();
    List<UserResponseDTO> userRes = new ArrayList<>();
    for(UserEntity ue: users){
      UserResponseDTO urd = new UserResponseDTO(ue);
      userRes.add(urd);
    }

    return userRes;

  }

  @Transactional(rollbackFor = Exception.class)
  public UserResponseDTO createUser(UserCreateRequestDTO userCreateRequestDTO){
    UserEntity userEntity = new UserEntity(userCreateRequestDTO.getUserName(), userCreateRequestDTO.getUserGender(), userCreateRequestDTO.getUserAge());
    userEntity.setEmail(userCreateRequestDTO.getEmail());
    if(userCreateRequestDTO.getPassword().equals(userCreateRequestDTO.getPasswordConfirmation())){
      String encodedPassword = passwordEncoder.encode(userCreateRequestDTO.getPassword());
      userEntity.setPassword(encodedPassword);  
    }else{
      throw new UserNotAuthenticatedException("password and password confirmation does not match");
    }
    userEntity.setEmail(userCreateRequestDTO.getEmail());

    UserEntity ue = userRepository.save(userEntity);
    return new UserResponseDTO(ue);
  }

  @Transactional(rollbackFor = Exception.class)
  public UserResponseDTO updateUser(Long id, UserResponseDTO userResponseDTO){
    Optional<UserEntity> userEntityOpt = userRepository.findById(id);
    UserEntity userEntity = userEntityOpt.get();

    userEntity.setAge(userResponseDTO.getUserAge());
    userEntity.setGender(userResponseDTO.getUserGender());
    userEntity.setName(userResponseDTO.getUserName());
    return new UserResponseDTO(userEntity);
  }

  public UserResponseDTO getUserById(Long id) throws UserNotFoundException{
    UserEntity userEntity = findUserById(id);
    return new UserResponseDTO(userEntity);
  }

  @Transactional(rollbackFor = Exception.class)
  public UserResponseDTO deleteUser(Long id){
    UserEntity userEntity = findUserById(id);
    userRepository.delete(userEntity);
    return new UserResponseDTO(userEntity);
  }

  public UserLoginResponseDTO loginUser(UserLoginDTO userLoginDTO){
    UserEntity userEntity = findByEmail(userLoginDTO.getEmail());
    //authenticate
    boolean passwordMatch = passwordEncoder.matches(userLoginDTO.getPassword(), userEntity.getPassword());
    if(passwordMatch){
      String token = jwtUtility.encode(userLoginDTO.getEmail());
      return new UserLoginResponseDTO(token);
    }else{
      throw new UserNotAuthenticatedException("email or password is incorrect");
    }
  }

  private UserEntity findByEmail(String email){
    UserEntity user = userRepository.findByEmail(email).orElseThrow(
      new Supplier<UserNotFoundException>(){
        public UserNotFoundException get(){
          return new UserNotFoundException("invalid email or password");
        }
      }
    );
    return user;
  }

  private UserEntity findUserById(Long id){
    UserEntity userEntity = userRepository.findById(id).orElseThrow(
      new Supplier<UserNotFoundException>() {
        public UserNotFoundException get(){
          return new UserNotFoundException("user with the id: " + id + " does not exists.");
        }
      }
    );
    return userEntity;
  }

}
