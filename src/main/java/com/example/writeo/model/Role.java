package com.example.writeo.model;

import com.example.writeo.enums.UserType;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserType name;

    public Role() {

    }

    public Role(UserType name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserType getName() {
        return name;
    }

    public void setName(UserType name) {
        this.name = name;
    }
}
