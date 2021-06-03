package com.example.writeo.controller;

import com.example.writeo.controllerService.services.SellService;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Sell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("sell")
public class SellController {
    @Autowired
    private SellService sellService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<Sell>> getAllSells() {
        try{
            List<Sell> sells = sellService.findAll();
            if(sells == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(sells, HttpStatus.OK);
        } catch (JPAException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Sell> getSellById(@PathVariable("id") long id) {
        Optional<Sell> sellData = sellService.findById(id);
        return sellData.map(sell -> new ResponseEntity<>(sell, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Sell> createSell(@RequestBody Sell sell) {
        try {
            sell.setDateOfPurchase(LocalDate.now());
            sellService.save(sell);
            return new ResponseEntity<>(sell, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Sell> updateSell(@PathVariable("id") long id, @RequestBody Sell sell) {
        try{
            return new ResponseEntity<>(sellService.updateSell(sell, id), HttpStatus.OK);
        }catch (NullPointerException e)
        {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteSell(@PathVariable("id") long id) {
        try {
            sellService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteAllSells() {
        try {
            sellService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
