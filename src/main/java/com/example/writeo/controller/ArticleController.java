package com.example.writeo.controller;

import com.example.writeo.model.Article;
import com.example.writeo.repository.ArticleRepository;
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
@RequestMapping("article")
public class ArticleController {
@Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/all")
    //@PreAuthorize("hasRole('Author')")
    public ResponseEntity<List<Article>> getAllArticles() {
        try {
            List<Article> articles = new ArrayList<Article>(articleRepository.findAll());
            if (articles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(articles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('Author')")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") long id) {
        Optional<Article> articleData = articleRepository.findById(id);

        return articleData.map(article -> new ResponseEntity<>(article, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('Author')")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        try {
            articleRepository.save(article);
            return new ResponseEntity<>(article, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasRole('Author')")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody Article article)
    {
        Optional<Article> articleData = articleRepository.findById(id);

        if (articleData.isPresent()) {
            Article _article = articleData.get();
            _article.setArticleTitle(article.getArticleTitle());
            _article.setArticleStatus(article.getArticleStatus());
            _article.setArticlePrice(article.getArticlePrice());
            _article.setArticleContent(article.getArticleContent());
            _article.setArticlePublished(article.getArticlePublished());
            articleRepository.save(article);
            return new ResponseEntity<>(article, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyRole('Author', 'Admin')")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") long id) {
        try {
            articleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
//    @PreAuthorize("hasAnyRole('Author', 'Admin')")
    public ResponseEntity<HttpStatus> deleteAllArticles() {
        try {
            articleRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
