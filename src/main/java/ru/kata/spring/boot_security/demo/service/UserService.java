package ru.kata.spring.boot_security.demo.service;

import model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> getAllUsers();
}
