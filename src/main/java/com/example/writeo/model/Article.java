package com.example.writeo.model;

import com.example.writeo.enums.ArticleStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id")
    private Long id;

    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "article_content")
    private String articleContent;

    @Column(name = "article_published")
    private Boolean articlePublished;

    @Column(name = "article_status")
    @Enumerated
    private ArticleStatus articleStatus;

    @Column(name = "article_price")
    private double articlePrice;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "article_author_id", referencedColumnName = "user_id")
    private User author;

    public Article() {
    }

    public Article(String articleTitle, String articleContent, Boolean articlePublished, ArticleStatus articleStatus, double articlePrice, User author) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.articlePublished = articlePublished;
        this.articleStatus = articleStatus;
        this.articlePrice = articlePrice;
        this.author = author;
    }

    public Article(Long id, String articleTitle, String articleContent, Boolean articlePublished, ArticleStatus articleStatus, double articlePrice, User author) {
        this.id = id;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.articlePublished = articlePublished;
        this.articleStatus = articleStatus;
        this.articlePrice = articlePrice;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Boolean getArticlePublished() {
        return articlePublished;
    }

    public void setArticlePublished(Boolean articlePublished) {
        this.articlePublished = articlePublished;
    }

    public ArticleStatus getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(ArticleStatus status) {
        this.articleStatus = status;
    }

    public double getArticlePrice() {
        return articlePrice;
    }

    public void setArticlePrice(double price) {
        this.articlePrice = price;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(getId(), article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", articlePublished=" + articlePublished +
                ", status=" + articleStatus +
                ", price=" + articlePrice +
                ", author=" + author +
                '}';
    }
}