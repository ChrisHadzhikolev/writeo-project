package com.example.writeo.repository;

import com.example.writeo.enums.UserType;
import com.example.writeo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserType name);
}
