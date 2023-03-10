package org.launchcode.meander.controllers;

import org.launchcode.meander.models.User;
import org.launchcode.meander.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public String viewHomePage(Model model) {
        model.addAttribute("title", "Welcome to Meander");
        return "index";
    }

    @GetMapping("/login")
    public String loginIn(Model model) {
        model.addAttribute("title", "Log in");
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("title", "Become a Meanderer");
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "register_success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("title", "View all users");
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);

        return "users";
    }
}
