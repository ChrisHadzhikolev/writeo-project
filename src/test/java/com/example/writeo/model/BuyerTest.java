package com.example.writeo.model;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BuyerTest {
    long id = 0;
    String firstName = "John";
    String lastName = "Doe";
    double paid = 45;
    Buyer buyer = new Buyer(id, firstName, lastName, paid);

    @Test
    void getId() {
        assertEquals(0, buyer.getId());
    }

    @Test
    void setId() {
        buyer.setId((long) 6);
        assertEquals(6, buyer.getId());
    }

    @Test
    void getBuyerFirstName() {
        assertEquals("John", buyer.getBuyerFirstName());
    }

    @Test
    void setBuyerFirstName() {
        buyer.setBuyerFirstName("Jane");
        assertEquals("Jane", buyer.getBuyerFirstName());
    }

    @Test
    void getBuyerLastName() {
        assertEquals("Doe", buyer.getBuyerLastName());
    }

    @Test
    void setBuyerLastName() {
        buyer.setBuyerLastName("Hale");
        assertEquals("Hale", buyer.getBuyerLastName());
    }

    @Test
    void getBuyerSpentMoney() {
        assertEquals(45, buyer.getBuyerSpentMoney());
    }

    @Test
    void setBuyerSpentMoney() {
        buyer.setBuyerSpentMoney(66.0);
        assertEquals(66, buyer.getBuyerSpentMoney());
    }
}