package com.example.writeo.model;

import com.example.writeo.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RoleTest{
    int id = 0;
    Role role = new Role(id, UserType.ROLE_AUTHOR);

    @Test
    void getId() {
        assertEquals(id, role.getId());
    }

    @Test
    void setId() {
        role.setId(6);
        assertEquals(6, role.getId());
    }

    @Test
    void getRole() {
        assertEquals(UserType.ROLE_AUTHOR, role.getName());
    }

    @Test
    void setRole() {
        role.setName(UserType.ROLE_ADMIN);
        assertEquals(UserType.ROLE_ADMIN, role.getName());
    }
}