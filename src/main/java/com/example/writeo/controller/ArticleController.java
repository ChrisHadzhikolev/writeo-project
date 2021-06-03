package com.example.writeo.controller;

import com.example.writeo.controllerService.services.ArticleService;
import com.example.writeo.enums.ArticleStatus;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;
import com.example.writeo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = {"*", "*"})
@RequestMapping("article")
public class ArticleController {
@Autowired
    private ArticleService articleService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<List<Article>> getAllArticles() {
        try{
            List<Article> articles = articleService.findAll();
            if(articles == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(articles, HttpStatus.OK);
        } catch (JPAException e) {
            return new ResponseEntity("500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/author")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<List<Article>> getAllByAuthor(@RequestBody User author) {
        try{
            System.out.println("hdkjh"+author.toString());
            List<Article> articles = articleService.findAllByAuthor(author.getId());

            if(articles == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(articles, HttpStatus.OK);
        } catch (JPAException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/available")
    public ResponseEntity<List<Article>> getAllAvailableArticles() {
        try{
            List<Article> articles = articleService.findAllAvailable();
            if(articles == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(articles, HttpStatus.OK);
        } catch (JPAException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") long id) {
        Optional<Article> articleData = articleService.findById(id);
        return articleData.map(article -> new ResponseEntity<>(article, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/title/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Article> getArticleTitleById(@PathVariable("id") long id) {
        Optional<Article> articleData = articleService.findById(id);
        Article article = new Article();
        article.setArticleTitle(articleData.get().getArticleTitle());
        System.out.println(article.toString());
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        try {
            articleService.save(article);
            return new ResponseEntity<>(article, HttpStatus.CREATED);
        } catch (NullPointerException | JPAException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody Article article) throws JPAException {
       try{
           return new ResponseEntity<>(articleService.updateArticle(article, id), HttpStatus.OK);
       }catch (NullPointerException e)
       {
           return new ResponseEntity("500", HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @PutMapping("/sold/{id}")
    public ResponseEntity<Article> articleSold(@PathVariable("id") long id) throws JPAException {
        try{
            Optional<Article> articleOptional = articleService.findById(id);
            if(articleOptional.isPresent()){
                Article article = articleOptional.get();
                article.setArticleStatus(ArticleStatus.Sold);
                return new ResponseEntity<>(articleService.updateArticle(article, id), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e)
        {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") long id) {
        try {
            articleService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<HttpStatus> deleteAllArticles() {
        try {
            articleService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
