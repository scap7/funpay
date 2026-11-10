package com.example.funpay;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
@SpringBootTest
@Transactional 
class ScratchpadTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void runExperiment() {
        // Run ad-hoc queries with full breakpoint/debugger support
        var user = userRepository.save(new UserEntity("Abdul", "male", 29));
        System.out.println("Saved: " + user.toString());

        userRepository.findAll().forEach(u -> System.out.println(u.toString()));
        System.out.println(userRepository.findAll().size());
    }

    @AutoConfigureMockMvc
    void getUser(){
      
    }
}