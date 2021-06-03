package com.example.writeo.controllerService.interfaces;

import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Role;
import com.example.writeo.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAll() throws JPAException;
    List<User> findAllByRole(Role role) throws JPAException;
    User save(User user);
    Optional<User> findById(long id);
    void deleteById(long id);
    void deleteAll();
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
