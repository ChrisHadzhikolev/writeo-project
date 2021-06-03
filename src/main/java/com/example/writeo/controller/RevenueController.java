package com.example.writeo.controller;

import com.example.writeo.controllerService.services.RevenueService;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Revenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("revenue")
@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_ADMIN')")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;

    @GetMapping("/all")
    public ResponseEntity<List<Revenue>> getAllRevenueRecords() {
        try{
            List<Revenue> revenues = revenueService.findAll();
            if(revenues == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(revenues, HttpStatus.OK);
        } catch (JPAException e) {
            return new ResponseEntity("500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revenue> getRevenueRecordById(@PathVariable("id") long id) {
        Optional<Revenue> revenueData = revenueService.findById(id);
        return revenueData.map(revenue -> new ResponseEntity<>(revenue, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Revenue> createRevenue(@RequestBody Revenue revenue) {
        try {
            revenueService.save(revenue);
            return new ResponseEntity<>(revenue, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Revenue> updateRevenue(@PathVariable("id") long id, @RequestBody Revenue revenue) {
        try{
            return new ResponseEntity<>(revenueService.updateRevenue(revenue, id), HttpStatus.OK);
        }catch (NullPointerException e)
        {
            return new ResponseEntity("500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRevenueRecord(@PathVariable("id") long id) {
        try {
            revenueService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllRevenueRecords() {
        try {
            revenueService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
