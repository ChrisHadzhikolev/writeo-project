package com.example.writeo.model;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SellTest {

    long id = 0;
        Article article = new Article();
        Buyer buyer = new Buyer();
        LocalDate localDate = LocalDate.now();
        double price = 45;
        Sell sell = new Sell(id, article, buyer, localDate, price);
    @Test
    void getId() {
        assertEquals(0, sell.getId());
    }

    @Test
    void setId() {
        sell.setId((long) 6);
        assertEquals(6, sell.getId());
    }

    @Test
    void getArticle() {
        assertEquals(article, sell.getArticle());
    }

    @Test
    void setArticle() {
        Article a = new Article();
        a.setId((long) 6);
        sell.setArticle(a);
        assertEquals(a, sell.getArticle());
    }

    @Test
    void getBuyer() {
        assertEquals(buyer, sell.getBuyer());
    }

    @Test
    void setBuyer() {
        Buyer b = new Buyer();
        b.setId((long) 6);
        sell.setBuyer(b);
        assertEquals(b, sell.getBuyer());
    }

    @Test
    void getDateOfPurchase() {
        assertEquals(localDate, sell.getDateOfPurchase());
    }

    @Test
    void setDateOfPurchase() {
        LocalDate l  = LocalDate.now();
        sell.setDateOfPurchase(l);
        assertEquals(l, sell.getDateOfPurchase());
    }

    @Test
    void getSellPrice() {
        assertEquals(45, sell.getSellPrice());
    }

    @Test
    void setSellPrice() {
        sell.setSellPrice(66);
        assertEquals(66, sell.getSellPrice());
    }
}