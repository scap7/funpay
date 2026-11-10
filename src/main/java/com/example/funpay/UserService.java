package com.example.funpay;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.funpay.Exceptions.UserNotFoundException;
import com.example.funpay.dto.UserLoginDTO;
import com.example.funpay.dto.UserLoginResponseDTO;
import com.example.funpay.dto.UserResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service 
public class UserService {
  private UserRepository userRepository;
  private BCryptPasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
    this.passwordEncoder  = new BCryptPasswordEncoder();
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
  public UserResponseDTO createUser(UserResponseDTO userResponseDTO){
    UserEntity userEntity = new UserEntity(userResponseDTO.getUserName(), userResponseDTO.getUserGender(), userResponseDTO.getUserAge());
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
    passwordEncoder.matches(userLoginDTO.getPassword(), userEntity.getPassword());
    return new UserLoginResponseDTO(userEntity);

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
