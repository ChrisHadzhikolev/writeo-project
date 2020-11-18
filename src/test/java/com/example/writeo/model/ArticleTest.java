package com.example.writeo.model;

import com.example.writeo.enums.ArticleStatus;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ArticleTest {
    long id = 0;
    String title = "asd";
    String content = "asd-content";
    Boolean published = false;
    ArticleStatus articleStatus = ArticleStatus.FreeToUse;
    double articlePrice  = 10;
    User author = new User();
    Article article = new Article(
            id,
            title,
            content,
            published,
            articleStatus,
            articlePrice,
            author
    );
    @Test
    void getId() {
        assertEquals(0, article.getId());
    }

    @Test
    void setId() {
        article.setId((long) 6);
        assertEquals(6, article.getId());
    }

    @Test
    void getArticleTitle() {
        assertEquals("asd", article.getArticleTitle());
    }

    @Test
    void setArticleTitle() {
        article.setArticleTitle("asd-changed");
        assertEquals("asd-changed", article.getArticleTitle());
    }

    @Test
    void getArticleContent() {
        assertEquals("asd-content", article.getArticleContent());
    }

    @Test
    void setArticleContent() {
        article.setArticleContent("asd-content-changed");
        assertEquals("asd-content-changed", article.getArticleContent());
    }

    @Test
    void getArticlePublished() {
        assertEquals(false, article.getArticlePublished());
    }

    @Test
    void setArticlePublished() {
        article.setArticlePublished(true);
        assertEquals(true, article.getArticlePublished());
    }

    @Test
    void getStatus() {
        assertEquals(ArticleStatus.FreeToUse, article.getArticleStatus());
    }

    @Test
    void setStatus() {
        article.setArticleStatus(ArticleStatus.Auction);
        assertEquals(ArticleStatus.Auction, article.getArticleStatus());
    }

    @Test
    void getPrice() {
        assertEquals(10, article.getArticlePrice());
    }

    @Test
    void setPrice() {
        article.setArticlePrice(15);
        assertEquals(15, article.getArticlePrice());
    }

    @Test
    void getAuthor() {
        assertEquals(new User(), article.getAuthor());
    }

    @Test
    void setAuthor() {
        User user = new User();
        user.setId((long) 8);
        article.setAuthor(user);
        assertEquals(user.getId(), article.getAuthor().getId());
    }
}