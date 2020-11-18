package com.example.writeo.controller;

import com.example.writeo.enums.Gender;
import com.example.writeo.enums.UserType;
import com.example.writeo.model.*;
import com.example.writeo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(controllers = UserController.class, properties = {"spring.datasource.url=jdbc:h2:mem:virtual", "spring.datasource.username=sa", "spring.datasource.password=password"})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<User> userList;

    @BeforeEach
    void setup() {
        this.userList = new ArrayList<>();
        this.userList.add(new User(0L, "Alisson", "Argent", "aa11", Gender.Female, "iyfug787g8g87fg8gf7g8f7g87gf87g8fg87gf87gr87g", "a.argent@mail.fr", "Huntress..."));
        this.userList.add(new User(1L, "Scott", "McCall", "true_alpha", Gender.Male, "iyfug7hhkdhficuhiduhiudhiufhifhuff87gr87gjfj", "s.mccal@mail.com", "Alpha..."));
        this.userList.add(new User(2L, "Lydia", "Martin", "scream77", Gender.Female, "iyfughv7ryhv87hr78hv87reh8v7rhv8hre87vh88hbg", "a.argent@mail.com", "Banshee duh..."));

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
    }


    @Test
    void getAllUsers() throws Exception{
        given(userRepository.findAll()).willReturn(userList);
        this.mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(userList.size())));
    }

    @Test
    void getUserById() throws Exception{
        final long userId = 0L;
        final User user = userList.get(0);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        this.mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.gender", is(user.getGender().toString())))
                .andExpect(jsonPath("$.encPwd", is(user.getEncPwd())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.bio", is(user.getBio())));
                //.andExpect(jsonPath("$.userType", is(user.getUserType().toString())));
    }

    @Test
    void getUserByIdNotFound() throws Exception{
        final long userId = 7L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser() throws Exception{
        given(userRepository.save(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));
        User user = userList.get(0);

        this.mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.gender", is(user.getGender().toString())))
                .andExpect(jsonPath("$.encPwd", is(user.getEncPwd())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.bio", is(user.getBio())));
                //.andExpect(jsonPath("$.userType", is(user.getUserType().toString())));

    }

    @Test
    void updateUser() throws Exception{
        long userId = 0L;
        User user = userList.get(0);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(put("/user/update/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.gender", is(user.getGender().toString())))
                .andExpect(jsonPath("$.encPwd", is(user.getEncPwd())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.bio", is(user.getBio())));
                //.andExpect(jsonPath("$.userType", is(user.getUserType().toString())));
    }

    @Test
    void updateNonExistentUser() throws Exception{
        long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        User user = userList.get(1);

        this.mockMvc.perform(put("/user/update/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser() throws Exception{
        long userId = 0L;
        User user = userList.get(0);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(user.getId());

        this.mockMvc.perform(delete("/user/delete/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllUsers() throws Exception{
        given(userRepository.findAll()).willReturn(userList);
        doNothing().when(userRepository).deleteAll();

        this.mockMvc.perform(delete("/user/deleteAll"))
                .andExpect(status().isNoContent());
    }
}