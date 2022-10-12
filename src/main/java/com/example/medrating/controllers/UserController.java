package com.example.medrating.controllers;

import com.example.medrating.models.Role;
import com.example.medrating.models.User;
import com.example.medrating.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String logIn(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    public String regView(Model model){
        return "registration";
    }
    @PostMapping("/registration")
    public String regAction(Model model, User user){
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null){
            model.addAttribute("error", "Пользователь с таким именем существует");
            return "registration";
        }
        user.setActive(Boolean.TRUE);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
