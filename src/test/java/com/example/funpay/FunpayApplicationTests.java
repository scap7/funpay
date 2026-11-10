package com.example.funpay;

import java.util.LinkedHashMap;

import org.json.JSONString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc 
@ActiveProfiles("test")
class FunpayApplicationTests {
  @Autowired 
  private MockMvc mockMvc;
  @Autowired 
  private UserRepository userRepository;
private ObjectMapper objectMapper = new ObjectMapper();

	@Test
  void getUser1() throws Exception {
        // Arrange (Rails: user = User.create!(name: "Abdul", gender: "male", age: 29))
        UserEntity savedUser = userRepository.save(new UserEntity("Abdul", "male", 29));

        // Act & Assert (Rails: get "/api/v1/users/#{savedUser.id}")
        mockMvc.perform(get("/api/v1/users/{id}", savedUser.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) // Prints full HTTP request & response in terminal
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Abdul"))
                .andExpect(jsonPath("$.userGender").value("male"))
                .andExpect(jsonPath("$.userAge").value(29));
  }

  @Test 
  void loginUser() throws Exception{
    UserEntity savedUser = userRepository.save(new UserEntity("Abdul", "male", 29));
    savedUser.setEmail("abdul.test@gmail.com");
    Map<String, String> requestBody = new LinkedHashMap<>();
    requestBody.put("email", "test@gmail.com");
    requestBody.put("password", "my-password");
    String request = objectMapper.writeValueAsString(requestBody);


    mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isOk());
  }

}
