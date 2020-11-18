package com.example.writeo.controller;

import com.example.writeo.model.Buyer;
import com.example.writeo.repository.BuyerRepository;
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
@RequestMapping("buyer")
public class BuyerController {
    @Autowired
    private BuyerRepository buyerRepository;

    @GetMapping("/all")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Buyer>> getAllBuyers() {
        try {
            List<Buyer> buyers = new ArrayList<Buyer>(buyerRepository.findAll());
            if (buyers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(buyers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Buyer> getBuyerById(@PathVariable("id") long id) {
        Optional<Buyer> buyerData = buyerRepository.findById(id);

        return buyerData.map(buyer -> new ResponseEntity<>(buyer, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Buyer> createBuyer(@RequestBody Buyer buyer) {
        try {
            buyerRepository.save(buyer);
            return new ResponseEntity<>(buyer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Buyer> updateBuyer(@PathVariable("id") long id, @RequestBody Buyer buyer)
    {
        Optional<Buyer> buyerData = buyerRepository.findById(id);

        if (buyerData.isPresent()) {
            Buyer _buyer = buyerData.get();
            _buyer.setBuyerFirstName(buyer.getBuyerFirstName());
            _buyer.setBuyerLastName(buyer.getBuyerLastName());
            _buyer.setBuyerSpentMoney(buyer.getBuyerSpentMoney());
            buyerRepository.save(buyer);
            return new ResponseEntity<>(buyer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteBuyer(@PathVariable("id") long id) {
        try {
            buyerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteAllBuyers() {
        try {
            buyerRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
