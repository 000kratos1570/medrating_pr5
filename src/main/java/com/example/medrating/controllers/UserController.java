package com.example.medrating.controllers;

import com.example.medrating.models.*;
import com.example.medrating.repository.AccauntRepository;
import com.example.medrating.repository.PreporatRepository;
import com.example.medrating.repository.StarRepository;
import com.example.medrating.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccauntRepository accauntRepository;

    @GetMapping("/")
    public String logIn(Model model) {
        return "login";
    }

    @GetMapping("/home")
    public String logInPage(Model model) {
        return "home";
    }

    @Autowired
    PasswordEncoder passwordEncoder;
    @GetMapping("/registration")
    public String regView(Model model){
        return "registration";
    }
    @PostMapping("/registration")
    public String regAction(Model model, User user, Accaunt accaunt){
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null){
            model.addAttribute("error", "Пользователь с таким именем существует");
            return "registration";
        }
        user.setActive(Boolean.TRUE);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        accaunt.setStatus(" ");
        accaunt.setUser(user);
        accauntRepository.save(accaunt);
        return "redirect:/login";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/profiles")
    public String allUsers(Model model)
    {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("user", users);
        return "users/profiles";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/profiles/{id}")
    public String userDetails(@PathVariable(value = "id") long id, Model model){
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        if (!userRepository.existsById(id)){
            return "redirect:/";
        }
        return "users/details";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/profiles/{id}/update")
    public String userEdit(@PathVariable(value = "id") long id, Model model, User user){
        if (!userRepository.existsById(id)){
            return "redirect:/home";
        }
        user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "users/update";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/profiles/{id}/update")
    public String userUpd(@PathVariable(value = "id") long id, Model model,
                           @Valid User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "users/update";
        }

        userRepository.save(user);
        return "redirect:/profiles";
    }

    User mauser;
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/profiles/{id}/updateacc")
    public String userEditAcc(@PathVariable(value = "id") long id, Model model,User user, Accaunt accaunt){
        if (!accauntRepository.existsById(id)){
            return "redirect:/home";
        }
        accaunt = accauntRepository.findById(id).orElseThrow();
        mauser = accaunt.getUser();
        model.addAttribute("user",user);
        model.addAttribute("acc", accaunt);
        return "users/update-acc";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/profiles/{id}/updateacc")
    public String userUpdAcc(@PathVariable(value = "id") long id, Model model,
                          @Valid Accaunt accaunt, User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "users/update-acc";
        }
        accaunt.setUser(mauser);
        accauntRepository.save(accaunt);
        return "redirect:/profiles";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("profiles/{id}/{idac}/del")
    public String userDelete(@PathVariable(value = "id") long id,
                             @PathVariable(value = "idac") long idac, Model model){
        Accaunt accaunt = accauntRepository.findById(idac).orElseThrow();
        User user = userRepository.findById(id).orElseThrow();
        accauntRepository.delete(accaunt);
        userRepository.delete(user);
        return "redirect:/profiles";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/profiles/search")
    public String searchUsers(Model model)
    {
        return "users/search";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/search/result")
    public String searchUsersResult(@RequestParam(required = false) String username, Model model)
    {
        if (username != null){
            User user = userRepository.findByUsername(username);
            model.addAttribute("user", user);
        }
        return "users/search";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/profiles/add")
    public String addView(Model model){
        return "users/add";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/profiles/add")
    public String addAction(Model model, User user, Accaunt accaunt){
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null){
            model.addAttribute("error", "Пользователь с таким именем существует");
            return "users/add";
        }
        user.setActive(Boolean.TRUE);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        accaunt.setStatus(" ");
        accaunt.setUser(user);
        accauntRepository.save(accaunt);
        return "redirect:/profiles";
    }

    @Autowired
    PreporatRepository preporatRepository;
    @Autowired
    StarRepository starRepository;
    @GetMapping("/star")
    public String starPreporat(Model model, Preporat preporat){
        Iterable<Preporat> preporats = preporatRepository.findAll();
        Iterable<Star> stars = starRepository.findAll();
        model.addAttribute("preporat", preporats);
        model.addAttribute("star", stars);
        return "rating";
    }

    @PostMapping("/star")
    public String setStarPreporat(Preporat preporat,
                                  @RequestParam(required = false) Long id,
                                  @RequestParam(required = false) Long starID,
                                  Model model){
        if (!preporatRepository.existsById(id) || !starRepository.existsById(starID)){
            return "redirect:/home";
        }
        preporat = preporatRepository.findById(id).orElseThrow();
        Star star = starRepository.findById(starID).orElseThrow();
        preporat.setStars(star);
        preporatRepository.save(preporat);
        return "redirect:/star";
    }
}
