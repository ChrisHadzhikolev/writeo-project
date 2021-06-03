package com.example.writeo.controller;

import com.example.writeo.controllerService.services.SellService;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;
import com.example.writeo.model.Buyer;
import com.example.writeo.model.Sell;
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

import java.time.LocalDate;
import java.util.*;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:virtual", "spring.datasource.username=sa", "spring.datasource.password=password"})
class SellControllerTest {
    @Autowired
    private SellController sellController;

    @MockBean
    private SellService sellService;

    private List<Sell> sellList;

    @BeforeEach
    void setup() {
        sellList = Arrays.asList(
                new Sell(0L, new Article(), new Buyer(), LocalDate.now(),60),
        new Sell(1L, new Article(), new Buyer(), LocalDate.now().minusMonths(1),20),
       new Sell(2L, new Article(), new Buyer(), LocalDate.now().minusDays(5),5)

        );
    }

    @AfterEach
    void tearDown() {}

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getAllSells() throws JPAException {
        Mockito.when(sellService.findAll()).thenReturn(sellList);
        List<Sell> actualSells = sellController.getAllSells().getBody();
        assertEquals(sellList, actualSells);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getSellById(){
        final long sellId = 1L;
        Mockito.when(sellService.findById(sellId)).thenReturn(Optional.ofNullable(sellList.get(1)));
        Sell actualSell = sellController.getSellById(sellId).getBody();
        HttpStatus httpStatus = sellController.getSellById(sellId).getStatusCode();
        assertEquals(sellList.get(1), actualSell);
        assertEquals(HttpStatus.OK, httpStatus);

    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getSellByIdNotFound(){
        final long sellId = 10L;
        Mockito.when(sellService.findById(sellId)).thenReturn(Optional.empty());
        Boolean body = sellController.getSellById(sellId).hasBody();
        HttpStatus httpStatus = sellController.getSellById(sellId).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, httpStatus);
        assertEquals(false, body);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void createSell() throws JPAException {
        Sell sell = sellList.get(0);
        Mockito.when(sellService.save(sell)).thenReturn(sellList.get(0));
        Sell actualSell = sellController.createSell(sell).getBody();
        HttpStatus httpStatus = sellController.createSell(sell).getStatusCode();

        assertEquals(sellList.get(0), actualSell);
        assertEquals(HttpStatus.CREATED, httpStatus);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void updateSell() throws JPAException {
        long sellId = 0L;
        Sell sell = sellList.get(0);
        sell.setSellPrice(500);
        Mockito.when(sellService.updateSell(sell, sellId)).thenReturn(sell);
        Mockito.when(sellService.save(sell)).thenReturn(sell);

        Sell actualSell = sellController.updateSell(sellId, sell).getBody();
        HttpStatus httpStatus = sellController.updateSell(sellId, sell).getStatusCode();

        assertEquals(sellList.get(0), actualSell);
        assertEquals(HttpStatus.OK, httpStatus);
    }


    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void deleteSell(){
        long sellId = 0L;
        Sell sell = sellList.get(0);
        Mockito.when(sellService.findById(sellId)).thenReturn(Optional.ofNullable(sell));
        HttpStatus httpStatus = sellController.deleteSell(sellId).getStatusCode();

        assertEquals(HttpStatus.NO_CONTENT, httpStatus);

        Mockito.verify(sellService).deleteById(sellId);
    }


    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void deleteAllSells(){
        HttpStatus httpStatus = sellController.deleteAllSells().getStatusCode();

        assertEquals(HttpStatus.NO_CONTENT,httpStatus);

        Mockito.verify(sellService).deleteAll();
    }
}