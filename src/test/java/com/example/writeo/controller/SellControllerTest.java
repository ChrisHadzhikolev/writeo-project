package com.example.writeo.controller;

import com.example.writeo.model.*;
import com.example.writeo.repository.SellRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;



@WebMvcTest(controllers = SellController.class, properties = {"spring.datasource.url=jdbc:h2:mem:virtual", "spring.datasource.username=sa", "spring.datasource.password=password"})
class SellControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellRepository sellRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Sell> sellList;

    @BeforeEach
    void setup() {
        this.sellList = new ArrayList<>();
        this.sellList.add(new Sell((long)0, new Article(), new Buyer(), LocalDate.now(),60));
        this.sellList.add(new Sell((long)1, new Article(), new Buyer(), LocalDate.now().minusMonths(1),20));
        this.sellList.add(new Sell((long)2, new Article(), new Buyer(), LocalDate.now().minusDays(5),5));

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
    }


    @Test
    void getAllSells() throws Exception{
        given(sellRepository.findAll()).willReturn(sellList);
        this.mockMvc.perform(get("/sell/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(sellList.size())));
    }

    @Test
    void getSellById() throws Exception{
        final long sellId = 0L;
        final Sell sell = sellList.get(0);
        given(sellRepository.findById(sellId)).willReturn(Optional.of(sell));

        this.mockMvc.perform(get("/sell/{id}", sellId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article", is(sell.getArticle()), Article.class))
                .andExpect(jsonPath("$.buyer", is(sell.getBuyer()), Buyer.class))
                .andExpect(jsonPath("$.dateOfPurchase", is(sell.getDateOfPurchase().toString())))
                .andExpect(jsonPath("$.sellPrice", is(sell.getSellPrice())));
    }

    @Test
    void getSellByIdNotFound() throws Exception{
        final long sellId = 7L;
        given(sellRepository.findById(sellId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/sell/{id}", sellId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createSell() throws Exception{
        given(sellRepository.save(any(Sell.class))).willAnswer((invocation) -> invocation.getArgument(0));
        Sell sell = sellList.get(0);

        this.mockMvc.perform(post("/sell/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sell)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.article", is(sell.getArticle()), Article.class))
                .andExpect(jsonPath("$.buyer", is(sell.getBuyer()), Buyer.class))
                .andExpect(jsonPath("$.dateOfPurchase", is(sell.getDateOfPurchase().toString())))
                .andExpect(jsonPath("$.sellPrice", is(sell.getSellPrice())));
    }

    @Test
    void updateSell() throws Exception{
        long sellId = 0L;
        Sell sell = sellList.get(0);
        given(sellRepository.findById(sellId)).willReturn(Optional.of(sell));
        given(sellRepository.save(any(Sell.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(put("/sell/update/{id}", sell.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sell)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article", is(sell.getArticle()), Article.class))
                .andExpect(jsonPath("$.buyer", is(sell.getBuyer()), Buyer.class))
                .andExpect(jsonPath("$.dateOfPurchase", is(sell.getDateOfPurchase().toString())))
                .andExpect(jsonPath("$.sellPrice", is(sell.getSellPrice())));
    }

    @Test
    void updateNonExistentSell() throws Exception{
        long sellId = 1L;
        given(sellRepository.findById(sellId)).willReturn(Optional.empty());
        Sell sell = sellList.get(1);

        this.mockMvc.perform(put("/sell/update/{id}", sellId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sell)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSell() throws Exception{
        long sellId = 0L;
        Sell sell = sellList.get(0);

        given(sellRepository.findById(sellId)).willReturn(Optional.of(sell));
        doNothing().when(sellRepository).deleteById(sell.getId());

        this.mockMvc.perform(delete("/sell/delete/{id}", sellId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllSells() throws Exception{
        given(sellRepository.findAll()).willReturn(sellList);
        doNothing().when(sellRepository).deleteAll();

        this.mockMvc.perform(delete("/sell/deleteAll"))
                .andExpect(status().isNoContent());
    }
}