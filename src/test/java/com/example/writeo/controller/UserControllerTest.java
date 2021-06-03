package com.example.writeo.controller;

import com.example.writeo.controllerService.services.UserService;
import com.example.writeo.enums.Gender;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:virtual", "spring.datasource.username=sa", "spring.datasource.password=password"})
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @BeforeEach
    void setup() {
        userList = Arrays.asList(
                new User(0L, "Alisson", "Argent", "aa11", Gender.Female, "iyfug787g8g87fg8gf7g8f7g87gf87g8fg87gf87gr87g", "a.argent@mail.fr", "Huntress..."),
                new User(1L, "Scott", "McCall", "true_alpha", Gender.Male, "iyfug7hhkdhficuhiduhiudhiufhifhuff87gr87gjfj", "s.mccal@mail.com", "Alpha..."),
                new User(2L, "Lydia", "Martin", "scream77", Gender.Female, "iyfughv7ryhv87hr78hv87reh8v7rhv8hre87vh88hbg", "a.argent@mail.com", "Banshee duh...")
        );
    }

    @AfterEach
    void tearDown() {}

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getAllUsers() throws JPAException {
        Mockito.when(userService.findAll()).thenReturn(userList);
        List<User> actualUsers = userController.getAllUsers().getBody();
        assertEquals(userList, actualUsers);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getUserById(){
        final long userId = 1L;
        Mockito.when(userService.findById(userId)).thenReturn(Optional.ofNullable(userList.get(1)));
        User actualUser = userController.getUserById(userId).getBody();
        HttpStatus httpStatus = userController.getUserById(userId).getStatusCode();
        assertEquals(userList.get(1), actualUser);
        assertEquals(HttpStatus.OK, httpStatus);

    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getUserByIdNotFound(){
        final long userId = 10L;
        Mockito.when(userService.findById(userId)).thenReturn(Optional.empty());
        Boolean body = userController.getUserById(userId).hasBody();
        HttpStatus httpStatus = userController.getUserById(userId).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, httpStatus);
        assertEquals(false, body);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void createUser() throws JPAException {
        User user = userList.get(0);
        Mockito.when(userService.save(user)).thenReturn(userList.get(0));
        User actualUser = userController.createUser(user).getBody();
        HttpStatus httpStatus = userController.createUser(user).getStatusCode();

        assertEquals(userList.get(0), actualUser);
        assertEquals(HttpStatus.CREATED, httpStatus);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void updateUser() throws JPAException {
        long userId = 0L;
        User user = userList.get(0);
        user.setBio("Hm");
        Mockito.when(userService.updateUser(user, userId)).thenReturn(user);
        Mockito.when(userService.save(user)).thenReturn(user);

        User actualUser = userController.updateUser(userId, user).getBody();
        HttpStatus httpStatus = userController.updateUser(userId, user).getStatusCode();

        assertEquals(userList.get(0), actualUser);
        assertEquals(HttpStatus.OK, httpStatus);
    }


    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void deleteUser(){
        long userId = 0L;
        User user = userList.get(0);
        Mockito.when(userService.findById(userId)).thenReturn(Optional.ofNullable(user));
        HttpStatus httpStatus = userController.deleteUser(userId).getStatusCode();

        assertEquals(HttpStatus.NO_CONTENT, httpStatus);

        Mockito.verify(userService).deleteById(userId);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void deleteAllUsers(){
        HttpStatus httpStatus = userController.deleteAllUsers().getStatusCode();

        assertEquals(HttpStatus.NO_CONTENT,httpStatus);

        Mockito.verify(userService).deleteAll();
    }
}