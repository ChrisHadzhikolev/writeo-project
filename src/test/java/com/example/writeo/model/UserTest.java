package com.example.writeo.model;

import com.example.writeo.enums.Gender;
import com.example.writeo.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserTest {
        long id = 0;
        String firstName = "John";
        String lastName = "Doe";
        String username = "jd23";
        Gender gender = Gender.Male;
        String encPwd = "encrguuydw87tr86t874387rtg87387g384gr83g";
        String email = "j.doe@gmail.com";
        String bio = "Some random bio here.";
        //UserType userType = UserType.Admin;

        User user = new User(
                id,
                firstName,
                lastName,
                username,
                gender,
                encPwd,
                email,
                bio
                //userType
        );

    @Test
    void getId() {
        assertEquals(0, user.getId());
    }

    @Test
    void setId() {
        user.setId((long) 6);
        assertEquals(6, user.getId());
    }

    @Test
    void getBio() {
        assertEquals("Some random bio here.", user.getBio());
    }

    @Test
    void setBio() {
        user.setBio("bio");
        assertEquals("bio", user.getBio());
    }

//    @Test
//    void getUserType() {
//        assertEquals(UserType.Admin, user.getUserType());
//    }

//    @Test
//    void setUserType() {
//        user.setUserType(UserType.Author);
//        assertEquals(UserType.Author, user.getUserType());
//    }

    @Test
    void getFirstName() {
        assertEquals("John", user.getFirstName());
    }

    @Test
    void setFirstName() {
        user.setFirstName("Malia");
        assertEquals("Malia", user.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doe", user.getLastName());
    }

    @Test
    void setLastName() {
        user.setLastName("Hale");
        assertEquals("Hale", user.getLastName());
    }

    @Test
    void getUsername() {
        assertEquals("jd23", user.getUsername());
    }

    @Test
    void setUsername() {
        user.setUsername("mh23");
        assertEquals("mh23", user.getUsername());
    }

    @Test
    void getGender() {
        assertEquals(Gender.Male, user.getGender());
    }

    @Test
    void setGender() {
        user.setGender(Gender.Female);
        assertEquals(Gender.Female, user.getGender());
    }

    @Test
    void getEncPwd() {
        assertEquals("encrguuydw87tr86t874387rtg87387g384gr83g", user.getEncPwd());
    }

    @Test
    void setEncPwd() {
        user.setEncPwd("hfukgfiougr9ti7griegtifrkuegfvicydso8yuiuy7yi");
        assertEquals("hfukgfiougr9ti7griegtifrkuegfvicydso8yuiuy7yi", user.getEncPwd());
    }

    @Test
    void getEmail() {
        assertEquals("j.doe@gmail.com", user.getEmail());
    }

    @Test
    void setEmail() {
        user.setEmail("m.hale@gmail.com");
        assertEquals("m.hale@gmail.com", user.getEmail());
    }
}