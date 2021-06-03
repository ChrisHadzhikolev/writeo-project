package com.example.writeo.controllerService.interfaces;

import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;
import com.example.writeo.model.Revenue;

import java.util.List;
import java.util.Optional;

public interface IRevenueService {
    List<Revenue> findAll() throws JPAException;
    Revenue save(Revenue revenue);
    Optional<Revenue> findById(long id);
    void deleteById(long id);
    void deleteAll();
}
