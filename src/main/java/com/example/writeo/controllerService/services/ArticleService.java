package com.example.writeo.controllerService.services;

import com.example.writeo.controllerService.interfaces.IArticleService;
import com.example.writeo.enums.ArticleStatus;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;
import com.example.writeo.model.User;
import com.example.writeo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService implements IArticleService {
    @Autowired
    private ArticleRepository articleRepository;


    @Override
    public List<Article> findAll() throws JPAException {
        try{
            List<Article> articles= articleRepository.findAll();
            if (articles.isEmpty()) return null;
            return articles;
        }catch (Exception e){
            throw new JPAException();
        }
    }

    @Override
    public List<Article> findAllAvailable() throws JPAException {
        try{
            List<Article> articles= articleRepository.findAll();
            List<Article> filteredArticles= new ArrayList<>();
            if (articles.isEmpty()) return null;
            for (Article a:articles
                 ) {
                if (a.getArticleStatus() != ArticleStatus.Sold && a.getArticleStatus() != ArticleStatus.NotForSale && a.getArticlePublished()) filteredArticles.add(a);
            }
            return filteredArticles;
        }catch (Exception e){
            throw new JPAException();
        }
    }

    @Override
    public List<Article> findAllByAuthor(Long authorId) throws JPAException {
        try{
            List<Article> articles= articleRepository.findAll();
            List<Article> filteredArticles= new ArrayList<>();
            if (articles.isEmpty()) return null;
            for (Article a:articles
            ) {
                System.out.println(authorId);
                if (a.getAuthor().getId() == authorId && a.getArticleStatus() != ArticleStatus.Sold){
                    filteredArticles.add(a);
                    System.out.println("+");
                }
            }
            return filteredArticles;
        }catch (Exception e){
            throw new JPAException();
        }
    }

    @Override
    public Article save(Article article) throws JPAException {
            if (article != null) {
                return articleRepository.save(article);
            }else throw new NullPointerException();
    }

    @Override
    public Optional<Article> findById(long id) {
        if(id >= 0L){
            return articleRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        articleRepository.deleteAll();
    }

    public Article updateArticle(Article changedArticle, long id){
        Optional<Article> articleData = articleRepository.findById(id);

        if (articleData.isPresent()) {
            changedArticle.setId(id);
            articleRepository.save(changedArticle);
            return changedArticle;
        } else {
            throw new NullPointerException();
        }
    }
}
