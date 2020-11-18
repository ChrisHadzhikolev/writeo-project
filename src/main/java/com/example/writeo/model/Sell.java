package com.example.writeo.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "sell")
public class Sell {
    @Column(name = "sell_id")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = Article.class)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private Article article;

    @ManyToOne(targetEntity = Buyer.class)
    @JoinColumn(name = "buyer_id", referencedColumnName = "buyer_id")
    private Buyer buyer;

    @Column(name = "date_of_purchase")
    private LocalDate dateOfPurchase;

    @Column(name = "sell_price")
    private double sellPrice;

    public Sell(Long id, Article article, Buyer buyer, LocalDate dateOfPurchase, double sellPrice) {
        this.id = id;
        this.article = article;
        this.buyer = buyer;
        this.dateOfPurchase = dateOfPurchase;
        this.sellPrice = sellPrice;
    }

    public Sell() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sell)) return false;
        Sell sell = (Sell) o;
        return Objects.equals(getId(), sell.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    @Override
    public String toString() {
        return "Sell{" +
                "id=" + id +
                ", article=" + article +
                ", buyer=" + buyer +
                ", dateOfPurchase=" + dateOfPurchase +
                ", sellPrice=" + sellPrice +
                '}';
    }
}
