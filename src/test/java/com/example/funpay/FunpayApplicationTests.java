package com.example.funpay;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc 
@ActiveProfiles("test")
class FunpayApplicationTests { 
  @Autowired 
  private MockMvc mockMvc;
  @Autowired 
  private UserRepository userRepository;
  private ObjectMapper objectMapper = new ObjectMapper();
  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Test
  void getUser() throws Exception {
        // Arrange (Rails: user = User.create!(name: "Abdul", gender: "male", age: 29))
        UserEntity savedUser = new UserEntity("Abdul", "male", 29);
        savedUser.setEmail("abdul.test@gmail.com");
        savedUser.setPassword(passwordEncoder.encode("nice-password"));

        savedUser = userRepository.save(savedUser);
        MvcResult response = mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(Map.of("email", "abdul.test@gmail.com", "password", "nice-password"))))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
        String responseBody = response.getResponse().getContentAsString();
        String token = objectMapper.readTree(responseBody).get("token").asText();
        System.out.println(token);
        // Act & Assert (Rails: get "/api/v1/users/#{savedUser.id}")
        mockMvc.perform(get("/api/v1/users/{id}", savedUser.getId())
                        .accept(MediaType.APPLICATION_JSON).header("x-api-key", token)) 
                .andDo(print()) // Prints full HTTP request & response in terminal
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Abdul"))
                .andExpect(jsonPath("$.userGender").value("male"))
                .andExpect(jsonPath("$.userAge").value(29));
  }
// incorrect password -- user not authenticated
  @Test 
  void loginWrongUser() throws Exception{
    UserEntity user = new UserEntity("Abdul", "male", 29);
    user.setEmail("abdul.test@gmail.com");
    user.setPassword("my-password");
    userRepository.save(user);
    Map<String, String> requestBody = new LinkedHashMap<>();
    requestBody.put("email", "abdul.test@gmail.com");
    requestBody.put("password", "my-password-wrong"); // wrong password
    String request = objectMapper.writeValueAsString(requestBody);

    mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON).content(request))
           .andExpect(status().isUnauthorized())
           .andExpect(jsonPath("$.message").value("email or password is incorrect"));
  }
// correct user login
  @Test
  void loginUser() throws Exception{
    UserEntity user = new UserEntity("Abdul", "male", 29);
    user.setEmail("abdul.test@gmail.com");
    user.setPassword(passwordEncoder.encode("my-password"));
    userRepository.save(user);
    Map<String, String> requestBody = new LinkedHashMap<>();
    requestBody.put("email", "abdul.test@gmail.com");
    requestBody.put("password", "my-password");
    String request = objectMapper.writeValueAsString(requestBody);

    mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON).content(request))
           .andExpect(status().isAccepted())
           .andExpect(jsonPath("$.token").isString())
           .andDo(print());
  }
  @Test
  void createUser() throws Exception{
    Map<String, Object> userRequest = new LinkedHashMap<>();
    userRequest.put("userName", "Rani");
    userRequest.put("email", "rani.test@gmail.com");
    userRequest.put("userGender", "female");
    userRequest.put("password", "my-password");
    userRequest.put("passwordConfirmation", "my-password");
    userRequest.put("userAge", 27);
    String userRequestJson = objectMapper.writeValueAsString(userRequest);
    mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(userRequestJson))
            .andExpect(status().isCreated())
           .andExpect(jsonPath("$.userName").value("Rani"))
           .andDo(print());
    Map<String, Object> loginReq = new LinkedHashMap<>();
    loginReq.put("email", userRequest.get("email"));
    loginReq.put("password", "my-password");
    String loginReqJson = objectMapper.writeValueAsString(loginReq);
    // login this user
    mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON).content(loginReqJson))
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.token").isString())
            .andDo(print())
            ;

  }

}
