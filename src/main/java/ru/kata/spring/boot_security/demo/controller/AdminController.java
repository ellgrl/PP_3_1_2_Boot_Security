package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("admin", userService.findByUsername(principal.getName()));
        return "admin/users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/new"; // шаблон new.html
    }

    // 🔹 Создание нового пользователя
    @PostMapping
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam("roles") List<Long> rolesIds) {
        Set<Role> roles = new HashSet<>(roleService.findRolesByIds(rolesIds));
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin";
    }

    // 🔹 Показ формы редактирования пользователя
    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/edit"; // шаблон edit.html
    }

    // 🔹 Обновление существующего пользователя
    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("roles") List<Long> rolesIds) {
        Set<Role> roles = new HashSet<>(roleService.findRolesByIds(rolesIds));
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin";
    }

    // 🔹 Удаление пользователя
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
