package com.example.writeo.controllerService.services;

import com.example.writeo.controllerService.interfaces.IUserService;
import com.example.writeo.enums.UserType;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Role;
import com.example.writeo.model.User;
import com.example.writeo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> findAll() throws JPAException {
        try{
            List<User> users= userRepository.findAll();
            if (users.isEmpty()) return null;
            return userRepository.findAll();
        }catch (Exception e){
            throw new JPAException();
        }
    }

    @Override
    public List<User> findAllByRole(Role role) throws JPAException {
        try{
            List<User> users= userRepository.findAll();
            List<User> filteredUsers = new ArrayList<>();
            if (users.isEmpty()){
                return null;
            }
            else{
                for (User user:users
                     ) {
                    if(user.getRoles().contains(role))
                    {
                        filteredUsers.add(user);
                    }
                }
            }
            return filteredUsers;
        }catch (Exception e){
            throw new JPAException();
        }
    }

    @Override
    public User save(User user){
        if (user != null) {
            return userRepository.save(user);
        }else throw new NullPointerException();
    }

    @Override
    public Optional<User> findById(long id) {
        if(id >= 0L){
            return userRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(long id) {
        Optional<User> optionalUser = findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            Role role = new Role();
            role.setName(UserType.ROLE_OWNER);
            if(!user.getRoles().contains(role)) userRepository.deleteById(id);
        }

    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return null;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return null;
    }

    public User updateUser(User changedUser, long id){
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            changedUser.setId(id);
            userRepository.save(changedUser);
            return changedUser;
        } else {
            throw new NullPointerException();
        }
    }
}
