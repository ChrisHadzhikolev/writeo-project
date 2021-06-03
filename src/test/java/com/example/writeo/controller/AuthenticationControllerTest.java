//package com.example.writeo.controller;
//
//import com.example.writeo.controllerService.services.ArticleService;
//import com.example.writeo.enums.ArticleStatus;
//import com.example.writeo.exception.JPAException;
//import com.example.writeo.model.Article;
//import com.example.writeo.model.User;
//import com.example.writeo.payload.request.LoginRequest;
//import com.example.writeo.payload.request.SignUpRequest;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:virtual", "spring.datasource.username=sa", "spring.datasource.password=password"})
//public class AuthenticationControllerTest {
//
//    @Autowired
//    private AuthenticationController authenticationController;
//
//
//
//    LoginRequest loginRequest = new LoginRequest();
//    SignUpRequest signUpRequest = new SignUpRequest();
//
//    @BeforeEach
//    void setup() {
//        signUpRequest.setUsername("aziska");
//        signUpRequest.setPassword("qawsedrf");
//        signUpRequest.setEmail("vp@gmail.com");
//        Set<String> roles = new HashSet<>();
//        roles.add("ROLE_AUTHOR");
//        signUpRequest.setRole(roles);
//
//        loginRequest.setUsername("aziska");
//        loginRequest.setPassword("qawsedrf");
//    }
//
//    @AfterEach
//    void tearDown() {}
//
//    @Test
//    void newUser(){
////        Mockito.when()
//    }
//}
