package com.example.writeo.controller;

import com.example.writeo.controllerService.services.ArticleService;
import com.example.writeo.enums.ArticleStatus;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;
import com.example.writeo.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:virtual", "spring.datasource.username=sa", "spring.datasource.password=password"})
class ArticleControllerTest {
    @Autowired
    private ArticleController articleController;

    @MockBean
    private ArticleService articleService;

    private List<Article> articleList;

    @BeforeEach
    void setup() {
        articleList = Arrays.asList(
                new Article(0L,"The Butcher of Blaviken", "A brave witcher...", true, ArticleStatus.FreeToUse, 15 ,new User()),
                new Article(1L,"The Alpha Pack", "Deucalion...", false, ArticleStatus.NotForSale, 25 ,new User()),
                new Article(2L,"The Shining", "Here is Johnnyyy!", true, ArticleStatus.ForSale, 1 ,new User())
        );
    }

    @AfterEach
    void tearDown() {}

    @Test
    @WithMockUser(roles = "AUTHOR", username = "aziska", password = "qawsedrf")
    void getAllArticles() throws JPAException {
        Mockito.when(articleService.findAll()).thenReturn(articleList);
        List<Article> actualArticles = articleController.getAllArticles().getBody();
        assertEquals(articleList, actualArticles);
    }

    @Test
    @WithMockUser(roles = "AUTHOR", username = "aziska", password = "qawsedrf")
    void getArticleById(){
        final long articleId = 1L;
        Mockito.when(articleService.findById(articleId)).thenReturn(Optional.ofNullable(articleList.get(1)));
        Article actualArticle = articleController.getArticleById(articleId).getBody();
        HttpStatus httpStatus = articleController.getArticleById(articleId).getStatusCode();
        assertEquals(articleList.get(1), actualArticle);
        assertEquals(HttpStatus.OK, httpStatus);

        }

    @Test
    @WithMockUser(roles = "AUTHOR", username = "aziska", password = "qawsedrf")
    void getArticleByIdNotFound(){
        final long articleId = 10L;
        Mockito.when(articleService.findById(articleId)).thenReturn(Optional.empty());
        Boolean body = articleController.getArticleById(articleId).hasBody();
        HttpStatus httpStatus = articleController.getArticleById(articleId).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, httpStatus);
        assertEquals(false, body);
    }

    @Test
    @WithMockUser(roles = "AUTHOR", username = "aziska", password = "qawsedrf")
    void createArticle() throws JPAException {
        Article article = articleList.get(0);
       Mockito.when(articleService.save(article)).thenReturn(articleList.get(0));
       Article actualArticle = articleController.createArticle(article).getBody();
       HttpStatus httpStatus = articleController.createArticle(article).getStatusCode();

        assertEquals(articleList.get(0), actualArticle);
        assertEquals(HttpStatus.CREATED, httpStatus);
    }

    @Test
    @WithMockUser(roles = "AUTHOR", username = "aziska", password = "qawsedrf")
    void updateArticle() throws JPAException {
        long articleId = 0L;
        Article article = articleList.get(0);
        article.setArticlePrice(78);
        Mockito.when(articleService.updateArticle(article, articleId)).thenReturn(article);
        Mockito.when(articleService.save(article)).thenReturn(article);

        Article actualArticle = articleController.updateArticle(articleId, article).getBody();
        HttpStatus httpStatus = articleController.updateArticle(articleId, article).getStatusCode();

        assertEquals(articleList.get(0), actualArticle);
        assertEquals(HttpStatus.OK, httpStatus);
    }


    @Test
    @WithMockUser(roles = "AUTHOR", username = "aziska", password = "qawsedrf")
    void deleteArticle(){
        long articleId = 0L;
        Article article = articleList.get(0);
            Mockito.when(articleService.findById(articleId)).thenReturn(Optional.ofNullable(article));
            HttpStatus httpStatus = articleController.deleteArticle(articleId).getStatusCode();

            assertEquals(HttpStatus.NO_CONTENT, httpStatus);

            Mockito.verify(articleService).deleteById(articleId);
    }


    @Test
    @WithMockUser(roles = "AUTHOR", username = "aziska", password = "qawsedrf")
    void deleteAllArticles(){
        HttpStatus httpStatus = articleController.deleteAllArticles().getStatusCode();
        assertEquals(HttpStatus.NO_CONTENT,httpStatus);
        Mockito.verify(articleService).deleteAll();


    }
}