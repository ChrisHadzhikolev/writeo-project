package com.example.writeo.controller;

import com.example.writeo.controllerService.services.RevenueService;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Revenue;
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
class RevenueControllerTest {
    @Autowired
    private RevenueController revenueController;

    @MockBean
    private RevenueService revenueService;

    private List<Revenue> revenueList;

    @BeforeEach
    void setup() {
        revenueList = Arrays.asList(
                new Revenue(0L, LocalDate.now(), 150),
                new Revenue(1L, LocalDate.now().plusDays(56), 250),
                new Revenue(2L, LocalDate.now().plusDays(60), 550)
        );
    }

    @AfterEach
    void tearDown() {}

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getAllRevenues() throws JPAException {
        Mockito.when(revenueService.findAll()).thenReturn(revenueList);
        List<Revenue> actualRevenues = revenueController.getAllRevenueRecords().getBody();
        assertEquals(revenueList, actualRevenues);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getRevenueById(){
        final long revenueId = 1L;
        Mockito.when(revenueService.findById(revenueId)).thenReturn(Optional.ofNullable(revenueList.get(1)));
        Revenue actualRevenue = revenueController.getRevenueRecordById(revenueId).getBody();
        HttpStatus httpStatus = revenueController.getRevenueRecordById(revenueId).getStatusCode();
        assertEquals(revenueList.get(1), actualRevenue);
        assertEquals(HttpStatus.OK, httpStatus);

    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void getRevenueByIdNotFound(){
        final long revenueId = 10L;
        Mockito.when(revenueService.findById(revenueId)).thenReturn(Optional.empty());
        Boolean body = revenueController.getRevenueRecordById(revenueId).hasBody();
        HttpStatus httpStatus = revenueController.getRevenueRecordById(revenueId).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, httpStatus);
        assertEquals(false, body);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void createRevenue() throws JPAException {
        Revenue revenue = revenueList.get(0);
        Mockito.when(revenueService.save(revenue)).thenReturn(revenueList.get(0));
        Revenue actualRevenue = revenueController.createRevenue(revenue).getBody();
        HttpStatus httpStatus = revenueController.createRevenue(revenue).getStatusCode();

        assertEquals(revenueList.get(0), actualRevenue);
        assertEquals(HttpStatus.CREATED, httpStatus);
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void updateRevenue() throws JPAException {
        long revenueId = 0L;
        Revenue revenue = revenueList.get(0);
        revenue.setRevenue(500);
        Mockito.when(revenueService.updateRevenue(revenue, revenueId)).thenReturn(revenue);
        Mockito.when(revenueService.save(revenue)).thenReturn(revenue);

        Revenue actualRevenue = revenueController.updateRevenue(revenueId, revenue).getBody();
        HttpStatus httpStatus = revenueController.updateRevenue(revenueId, revenue).getStatusCode();

        assertEquals(revenueList.get(0), actualRevenue);
        assertEquals(HttpStatus.OK, httpStatus);
    }


    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void deleteRevenue(){
        long revenueId = 0L;
        Revenue revenue = revenueList.get(0);
        Mockito.when(revenueService.findById(revenueId)).thenReturn(Optional.ofNullable(revenue));
        HttpStatus httpStatus = revenueController.deleteRevenueRecord(revenueId).getStatusCode();

        assertEquals(HttpStatus.NO_CONTENT, httpStatus);

        Mockito.verify(revenueService).deleteById(revenueId);
    }


    @Test
    @WithMockUser(roles = "ADMIN", username = "aziska", password = "qawsedrf")
    void deleteAllRevenues(){
        HttpStatus httpStatus = revenueController.deleteAllRevenueRecords().getStatusCode();

        assertEquals(HttpStatus.NO_CONTENT,httpStatus);

        Mockito.verify(revenueService).deleteAll();
    }
}