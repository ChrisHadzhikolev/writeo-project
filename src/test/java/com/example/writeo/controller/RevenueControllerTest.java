package com.example.writeo.controller;

import com.example.writeo.model.Revenue;
import com.example.writeo.repository.RevenueRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;


@WebMvcTest(controllers = RevenueController.class, properties = {"spring.datasource.url=jdbc:h2:mem:virtual", "spring.datasource.username=sa", "spring.datasource.password=password"})
class RevenueControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RevenueRepository revenueRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Revenue> revenueList;

    @BeforeEach
    void setup() {
        this.revenueList = new ArrayList<>();
        this.revenueList.add(new Revenue(0L, LocalDate.now(), 150));
        this.revenueList.add(new Revenue(1L, LocalDate.now().plusDays(56), 250));
        this.revenueList.add(new Revenue(2L, LocalDate.now().plusDays(60), 550));

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
    }
    @Test
    void getAllRevenueRecords() throws Exception{
        given(revenueRepository.findAll()).willReturn(revenueList);
        this.mockMvc.perform(get("/revenue/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(revenueList.size())));
    }

    @Test
    void getRevenueRecordById() throws Exception{
        final long revenueId = 0;
        final Revenue revenue = revenueList.get(0);
        given(revenueRepository.findById(revenueId)).willReturn(Optional.of(revenue));

        this.mockMvc.perform(get("/revenue/{id}", revenueId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.month_and_year", is(revenue.getMonth_and_year().toString())))
                .andExpect(jsonPath("$.revenue", is(revenue.getRevenue())));
    }

    @Test
    void getRevenueRecordByIdNotFound() throws Exception{
        final long revenueId = 0;
        given(revenueRepository.findById(revenueId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/revenue/{id}", revenueId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRevenue() throws Exception{
        given(revenueRepository.save(any(Revenue.class))).willAnswer((invocation) -> invocation.getArgument(0));
        Revenue revenue = revenueList.get(0);

        this.mockMvc.perform(post("/revenue/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(revenue)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.month_and_year", is(revenue.getMonth_and_year().toString())))
                .andExpect(jsonPath("$.revenue", is(revenue.getRevenue())));
    }

    @Test
    void updateRevenue() throws Exception{
        Long revenueId = 0L;
        Revenue revenue = revenueList.get(0);
        given(revenueRepository.findById(revenueId)).willReturn(Optional.of(revenue));
        given(revenueRepository.save(any(Revenue.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(put("/revenue/update/{id}", revenue.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(revenue)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.month_and_year", is(revenue.getMonth_and_year().toString())))
                .andExpect(jsonPath("$.revenue", is(revenue.getRevenue())));
    }

    @Test
    void updateNonExistentRevenueRecord() throws Exception{
        Long revenueId = 1L;
        given(revenueRepository.findById(revenueId)).willReturn(Optional.empty());
        Revenue revenue = revenueList.get(1);

        this.mockMvc.perform(put("/revenue/update/{id}", revenueId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(revenue)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRevenueRecord() throws Exception{
        long revenueId = 0L;
        Revenue revenue = revenueList.get(0);

        given(revenueRepository.findById(revenueId)).willReturn(Optional.of(revenue));
        doNothing().when(revenueRepository).deleteById(revenue.getId());

        this.mockMvc.perform(delete("/revenue/delete/{id}", revenueId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllRevenueRecords() throws Exception{
        given(revenueRepository.findAll()).willReturn(revenueList);
        doNothing().when(revenueRepository).deleteAll();

        this.mockMvc.perform(delete("/revenue/deleteAll"))
                .andExpect(status().isNoContent());
    }
}