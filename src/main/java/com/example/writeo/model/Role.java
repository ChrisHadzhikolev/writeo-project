package com.example.writeo.model;

import com.example.writeo.enums.UserType;

import javax.persistence.*;
import java.util.Objects;

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

    public Role(int id, UserType roleAuthor) {
        this.id = id;
        this.name=roleAuthor;
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

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getName() == role.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
