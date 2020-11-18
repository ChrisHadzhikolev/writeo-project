package com.example.writeo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "buyer")
public class Buyer {
    @Column(name = "buyer_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "buyer_first_name")
    private String buyerFirstName;

    @Column(name = "buyer_last_name")
    private String buyerLastName;

    @Column(name = "buyer_spent_money")
    private Double buyerSpentMoney;

    public Buyer(Long id, String buyerFirstName, String buyerLastName, Double buyerSpentMoney) {
        this.id = id;
        this.buyerFirstName = buyerFirstName;
        this.buyerLastName = buyerLastName;
        this.buyerSpentMoney = buyerSpentMoney;
    }

    public Buyer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyerFirstName() {
        return buyerFirstName;
    }

    public void setBuyerFirstName(String buyerFirstName) {
        this.buyerFirstName = buyerFirstName;
    }

    public String getBuyerLastName() {
        return buyerLastName;
    }

    public void setBuyerLastName(String buyerLastName) {
        this.buyerLastName = buyerLastName;
    }

    public Double getBuyerSpentMoney() {
        return buyerSpentMoney;
    }

    public void setBuyerSpentMoney(Double buyerSpentMoney) {
        this.buyerSpentMoney = buyerSpentMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Buyer)) return false;
        Buyer buyer = (Buyer) o;
        return Objects.equals(getId(), buyer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id=" + id +
                ", buyerFirstName='" + buyerFirstName + '\'' +
                ", buyerLastName='" + buyerLastName + '\'' +
                ", buyerSpentMoney=" + buyerSpentMoney +
                '}';
    }
}
