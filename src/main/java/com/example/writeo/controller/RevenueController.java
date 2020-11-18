package com.example.writeo.controller;

import com.example.writeo.model.Revenue;
import com.example.writeo.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("revenue")
//@PreAuthorize("hasAnyRole('Owner', 'Admin')")
public class RevenueController {
    @Autowired
    private RevenueRepository revenueRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Revenue>> getAllRevenueRecords() {
        try {
            List<Revenue> revenues = new ArrayList<Revenue>(revenueRepository.findAll());
            if (revenues.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(revenues, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revenue> getRevenueRecordById(@PathVariable("id") long id) {
        Optional<Revenue> revenueData = revenueRepository.findById(id);
        return revenueData.map(revenue -> new ResponseEntity<>(revenue, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Revenue> createRevenue(@RequestBody Revenue revenue) {
        try {
            revenueRepository.save(revenue);
            return new ResponseEntity<>(revenue, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Revenue> updateRevenue(@PathVariable("id") long id, @RequestBody Revenue revenue) {
        Optional<Revenue> revenueData = revenueRepository.findById(id);

        if (revenueData.isPresent()) {
            Revenue _revenue = revenueData.get();
            _revenue.setMonth_and_year(revenue.getMonth_and_year());
            _revenue.setRevenue(revenue.getRevenue());
            revenueRepository.save(_revenue);
            return new ResponseEntity<>(_revenue, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRevenueRecord(@PathVariable("id") long id) {
        try {
            revenueRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllRevenueRecords() {
        try {
            revenueRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
