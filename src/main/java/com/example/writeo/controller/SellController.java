package com.example.writeo.controller;

import com.example.writeo.model.Sell;
import com.example.writeo.repository.SellRepository;
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
@RequestMapping("sell")
//@PreAuthorize("hasAnyRole('Author', 'Admin')")
public class SellController {
    @Autowired
    private SellRepository sellRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Sell>> getAllSells() {
        try {
            List<Sell> sells = new ArrayList<Sell>(sellRepository.findAll());
            if (sells.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(sells, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sell> getSellById(@PathVariable("id") long id) {
        Optional<Sell> sellData = sellRepository.findById(id);

        return sellData.map(sell -> new ResponseEntity<>(sell, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Sell> createSell(@RequestBody Sell sell) {
        try {
            sellRepository.save(sell);
            return new ResponseEntity<>(sell, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Sell> updateSell(@PathVariable("id") long id, @RequestBody Sell sell) {
        Optional<Sell> sellData = sellRepository.findById(id);

        if (sellData.isPresent()) {
            Sell _sell = sellData.get();
            _sell.setArticle(sell.getArticle());
            _sell.setBuyer(sell.getBuyer());
            _sell.setDateOfPurchase(sell.getDateOfPurchase());
            _sell.setSellPrice(sell.getSellPrice());
            sellRepository.save(_sell);
            return new ResponseEntity<>(_sell, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSell(@PathVariable("id") long id) {
        try {
            sellRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllSells() {
        try {
            sellRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
