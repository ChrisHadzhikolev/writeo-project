package com.example.writeo.exception;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class JPAExceptionTest {
    JPAException e= new JPAException();
    @Test
    void getMessage() {
        assertEquals("JPA Method couldn't be executed!", e.getMessage());
    }
}
