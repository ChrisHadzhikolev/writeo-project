package com.example.writeo.controllerService.interfaces;

import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;
import com.example.writeo.model.Sell;

import java.util.List;
import java.util.Optional;

public interface ISellService {
    List<Sell> findAll() throws JPAException;
    Sell save(Sell sell);
    Optional<Sell> findById(long id);
    void deleteById(long id);
    void deleteAll();
}
