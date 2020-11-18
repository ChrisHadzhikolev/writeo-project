package com.example.writeo.model;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RevenueTest {
    long id = 0;
    LocalDate l = LocalDate.now();
    double revenueCount = 300;
    Revenue revenue = new Revenue(id, l, revenueCount);
    @Test
    void getId() {
        assertEquals(0, revenue.getId());
    }

    @Test
    void setId() {
        revenue.setId((long) 6);
        assertEquals(6, revenue.getId());
    }

    @Test
    void getMonth_and_year() {
        assertEquals(l, revenue.getMonth_and_year());
    }

    @Test
    void setMonth_and_year() {
        revenue.setMonth_and_year(l.plusDays(5));
        assertEquals(l.plusDays(5), revenue.getMonth_and_year());
    }

    @Test
    void getRevenue() {
        assertEquals(300, revenue.getRevenue());
    }

    @Test
    void setRevenue() {
        revenue.setRevenue(600);
        assertEquals(600, revenue.getRevenue());
    }
}