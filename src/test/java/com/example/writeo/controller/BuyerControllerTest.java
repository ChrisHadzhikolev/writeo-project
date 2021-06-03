package com.example.writeo.controller;

import com.example.writeo.controllerService.services.BuyerService;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Buyer;
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
class BuyerControllerTest {
    @Autowired
    private BuyerController buyerController;

    @MockBean
    private BuyerService buyerService;

    private List<Buyer> buyerList;

    @BeforeEach
    void setup() {
        buyerList = Arrays.asList(
                new Buyer(0L,"Peter","Hale", 1550.0),
                new Buyer(1L,"Malia","Hale", 10.0),
                new Buyer(2L,"Derek","Hale", 120.0)
        );
    }

    @AfterEach
    void tearDown() {}

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getAllBuyers() throws JPAException {
        Mockito.when(buyerService.findAll()).thenReturn(buyerList);
        List<Buyer> actualBuyers = buyerController.getAllBuyers().getBody();
        assertEquals(buyerList, actualBuyers);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getBuyerById(){
        final long BuyerId = 1L;
        Mockito.when(buyerService.findById(BuyerId)).thenReturn(Optional.ofNullable(buyerList.get(1)));
        Buyer actualBuyer = buyerController.getBuyerById(BuyerId).getBody();
        HttpStatus httpStatus = buyerController.getBuyerById(BuyerId).getStatusCode();
        assertEquals(buyerList.get(1), actualBuyer);
        assertEquals(HttpStatus.OK, httpStatus);

    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getBuyerByIdNotFound(){
        final long BuyerId = 10L;
        Mockito.when(buyerService.findById(BuyerId)).thenReturn(Optional.empty());
        Boolean body = buyerController.getBuyerById(BuyerId).hasBody();
        HttpStatus httpStatus = buyerController.getBuyerById(BuyerId).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, httpStatus);
        assertEquals(false, body);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void createBuyer() throws JPAException {
        Buyer Buyer = buyerList.get(0);
        Mockito.when(buyerService.save(Buyer)).thenReturn(buyerList.get(0));
        Buyer actualBuyer = buyerController.createBuyer(Buyer).getBody();
        HttpStatus httpStatus = buyerController.createBuyer(Buyer).getStatusCode();

        assertEquals(buyerList.get(0), actualBuyer);
        assertEquals(HttpStatus.CREATED, httpStatus);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void updateBuyer() throws JPAException {
        long BuyerId = 0L;
        Buyer Buyer = buyerList.get(0);
        Buyer.setBuyerFirstName("Bruno");
        Mockito.when(buyerService.updateBuyer(Buyer, BuyerId)).thenReturn(Buyer);
        Mockito.when(buyerService.save(Buyer)).thenReturn(Buyer);

        Buyer actualBuyer = buyerController.updateBuyer(BuyerId, Buyer).getBody();
        HttpStatus httpStatus = buyerController.updateBuyer(BuyerId, Buyer).getStatusCode();

        assertEquals(buyerList.get(0), actualBuyer);
        assertEquals(HttpStatus.OK, httpStatus);
    }


    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void deleteBuyer(){
        long BuyerId = 0L;
        Buyer Buyer = buyerList.get(0);
        Mockito.when(buyerService.findById(BuyerId)).thenReturn(Optional.ofNullable(Buyer));
        HttpStatus httpStatus = buyerController.deleteBuyer(BuyerId).getStatusCode();

        assertEquals(HttpStatus.NO_CONTENT, httpStatus);

        Mockito.verify(buyerService).deleteById(BuyerId);
    }


    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void deleteAllBuyers(){
        HttpStatus httpStatus = buyerController.deleteAllBuyers().getStatusCode();
        assertEquals(HttpStatus.NO_CONTENT,httpStatus);
        Mockito.verify(buyerService).deleteAll();


    }
}