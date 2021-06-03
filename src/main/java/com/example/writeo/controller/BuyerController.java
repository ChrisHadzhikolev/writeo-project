package com.example.writeo.controller;

import com.example.writeo.controllerService.services.BuyerService;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("buyer")
public class BuyerController {
    @Autowired
    private BuyerService buyerService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Buyer>> getAllBuyers() {
        try{
            List<Buyer> buyers = buyerService.findAll();
            if(buyers == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(buyers, HttpStatus.OK);
        } catch (JPAException e) {
            return new ResponseEntity("500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Buyer> getBuyerById(@PathVariable("id") long id) {
        Optional<Buyer> buyerData = buyerService.findById(id);
        return buyerData.map(buyer -> new ResponseEntity<>(buyer, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Buyer> createBuyer(@RequestBody Buyer buyer) {
        try {
            buyerService.save(buyer);
            return new ResponseEntity<>(buyer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Buyer> updateBuyer(@PathVariable("id") long id, @RequestBody Buyer buyer)
    {
        try{
            return new ResponseEntity<>(buyerService.updateBuyer(buyer, id), HttpStatus.OK);
        }catch (NullPointerException e)
        {
            return new ResponseEntity("500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteBuyer(@PathVariable("id") long id) {
        try {
            buyerService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteAllBuyers() {
        try {
            buyerService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
