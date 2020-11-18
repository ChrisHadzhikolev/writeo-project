package com.example.writeo.controller;

import com.example.writeo.model.*;
import com.example.writeo.repository.BuyerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;


@WebMvcTest(controllers = BuyerController.class, properties = {"spring.datasource.url=jdbc:h2:mem:virtual", "spring.datasource.username=sa", "spring.datasource.password=password"})
class BuyerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyerRepository buyerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Buyer> buyerList;

    @BeforeEach
    void setup() {
        this.buyerList = new ArrayList<>();
        this.buyerList.add(new Buyer(0L,"Peter","Hale", 1550.0));
        this.buyerList.add(new Buyer(1L,"Malia","Hale", 10.0));
        this.buyerList.add(new Buyer(2L,"Derek","Hale", 120.0));

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
    }

    @Test
    void getAllBuyers() throws Exception{
        given(buyerRepository.findAll()).willReturn(buyerList);
        this.mockMvc.perform(get("/buyer/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(buyerList.size())));
    }

    @Test
    void getBuyerById() throws Exception{
        final long buyerId = 0L;
        final Buyer buyer = buyerList.get(0);
        given(buyerRepository.findById(buyerId)).willReturn(Optional.of(buyer));

        this.mockMvc.perform(get("/buyer/{id}", buyerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyerFirstName", is(buyer.getBuyerFirstName())))
                .andExpect(jsonPath("$.buyerLastName", is(buyer.getBuyerLastName())))
                .andExpect(jsonPath("$.buyerSpentMoney", is(buyer.getBuyerSpentMoney())));
    }

    @Test
    void getBuyerByIdNotFound() throws Exception{
        final long buyerId = 7L;
        given(buyerRepository.findById(buyerId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/buyer/{id}", buyerId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBuyer() throws Exception{
        given(buyerRepository.save(any(Buyer.class))).willAnswer((invocation) -> invocation.getArgument(0));
        Buyer buyer = buyerList.get(0);

        this.mockMvc.perform(post("/buyer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buyer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.buyerFirstName", is(buyer.getBuyerFirstName())))
                .andExpect(jsonPath("$.buyerLastName", is(buyer.getBuyerLastName())))
                .andExpect(jsonPath("$.buyerSpentMoney", is(buyer.getBuyerSpentMoney())));
    }

    @Test
    void updateBuyer() throws Exception{
        long buyerId = 0L;
        Buyer buyer = buyerList.get(0);
        given(buyerRepository.findById(buyerId)).willReturn(Optional.of(buyer));
        given(buyerRepository.save(any(Buyer.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(put("/buyer/update/{id}", buyerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyerFirstName", is(buyer.getBuyerFirstName())))
                .andExpect(jsonPath("$.buyerLastName", is(buyer.getBuyerLastName())))
                .andExpect(jsonPath("$.buyerSpentMoney", is(buyer.getBuyerSpentMoney())));
    }

    @Test
    void updateNonExistentBuyer() throws Exception{
        long buyerId = 1L;
        given(buyerRepository.findById(buyerId)).willReturn(Optional.empty());
        Buyer buyer = buyerList.get(1);

        this.mockMvc.perform(put("/buyer/update/{id}", buyerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buyer)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBuyer() throws Exception{
        long buyerId = 0L;
        Buyer buyer = buyerList.get(0);

        given(buyerRepository.findById(buyerId)).willReturn(Optional.of(buyer));
        doNothing().when(buyerRepository).deleteById(buyer.getId());

        this.mockMvc.perform(delete("/buyer/delete/{id}", buyerId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllBuyers() throws Exception{
        given(buyerRepository.findAll()).willReturn(buyerList);
        doNothing().when(buyerRepository).deleteAll();

        this.mockMvc.perform(delete("/buyer/deleteAll"))
                .andExpect(status().isNoContent());
    }
}