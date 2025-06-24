package ru.kata.spring.boot_security.demo.service;

import model.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    void deleteRole(Long id);
    Role findByName(String name);
    List<Role> getAllRoles();
}