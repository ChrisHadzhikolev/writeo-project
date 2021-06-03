package com.example.writeo.controllerService.interfaces;

import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;

import java.util.List;
import java.util.Optional;

public interface IArticleService {
    List<Article> findAll() throws JPAException;
    List<Article> findAllAvailable() throws JPAException;
    List<Article> findAllByAuthor(Long author) throws JPAException;
    Article save(Article article) throws JPAException;
    Optional<Article> findById(long id);
    void deleteById(long id);
    void deleteAll();
}
