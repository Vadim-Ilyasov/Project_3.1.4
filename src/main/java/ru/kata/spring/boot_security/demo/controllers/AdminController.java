package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String showAllUsers(@ModelAttribute ("user") User user, Principal principal, Model model) {
        User authenticatedUser = userService.findByUsername(principal.getName());


        model.addAttribute ("authenticatedUser", authenticatedUser);
        model.addAttribute ("roleOfAuthenticatedUser", authenticatedUser.getRoles());
        model.addAttribute("users", userService.findAll());
        model.addAttribute( "AllRoles", roleService.findAll());
        return "admin";
    }

    @GetMapping("/user-profile/{id}")
    public String findUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("AllRoles", user.getRoles());
        return "user";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("AllRoles", roleService.findAll());
        return "redirect:/admin";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User updateUser, @PathVariable("id") Long id) {
        userService.updateUser(updateUser, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        List<Role> roles =  roleService.findAll();
        model.addAttribute("AllRoles", roles);
        return "redirect:/admin";
    }


    @PostMapping("/create")
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
