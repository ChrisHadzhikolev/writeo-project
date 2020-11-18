package com.example.writeo.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "revenue")
public class Revenue {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "revenue_id")
    private Long id;

    @Column(name = "revenue_month")
    private LocalDate month_and_year;

    @Column(name = "revenue_in_euros")
    private double revenue;

    public Revenue(Long id, LocalDate month_and_year, double revenue) {
        this.id = id;
        this.month_and_year = month_and_year;
        this.revenue = revenue;
    }

    public Revenue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMonth_and_year() {
        return month_and_year;
    }

    public void setMonth_and_year(LocalDate month_and_year) {
        this.month_and_year = month_and_year;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Revenue)) return false;
        Revenue revenue = (Revenue) o;
        return Objects.equals(getId(), revenue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Revenue{" +
                "id=" + id +
                ", month_and_year=" + month_and_year +
                ", revenue=" + revenue +
                '}';
    }
}

