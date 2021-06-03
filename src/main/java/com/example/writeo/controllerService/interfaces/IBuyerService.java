package com.example.writeo.controllerService.interfaces;

import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;
import com.example.writeo.model.Buyer;

import java.util.List;
import java.util.Optional;

public interface IBuyerService {
    List<Buyer> findAll() throws JPAException;
    Buyer save(Buyer buyer);
    Optional<Buyer> findById(long id);
    void deleteById(long id);
    void deleteAll();
}
