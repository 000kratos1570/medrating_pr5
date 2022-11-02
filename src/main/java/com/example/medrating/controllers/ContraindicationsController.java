package com.example.medrating.controllers;

import com.example.medrating.models.Contraindications;
import com.example.medrating.repository.ContraindicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/con")
public class ContraindicationsController {
    @Autowired
    ContraindicationsRepository contraindicationsRepository;

    @GetMapping("")
    public String allMaker(Model model)
    {
        Iterable<Contraindications> contraindications = contraindicationsRepository.findAll();
        model.addAttribute("contraindications", contraindications);
        return "contraindications/con-main";
    }

    @GetMapping("/add")
    public String addMaker(Model model, Contraindications contraindications){
        return "contraindications/con-add";
    }

    @PostMapping("/add")
    public String PostaddMaker(@Valid Contraindications contraindications, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "contraindications/con-add";
        }
        contraindicationsRepository.save(contraindications);
        return "redirect:/con";
    }

    @GetMapping("/{id}")
    public String MakerDetails(@PathVariable(value = "id") long id, Model model){
        Contraindications contraindications = contraindicationsRepository.findById(id).orElseThrow();
        model.addAttribute("contraindications", contraindications);

        if (!contraindicationsRepository.existsById(id)){
            return "redirect:/con";
        }
        return "contraindications/con-details";
    }


    @GetMapping("/{id}/update")
    public String makerEdit(@PathVariable(value = "id") long id, Model model, Contraindications contraindications){
        if (!contraindicationsRepository.existsById(id)){
            return "redirect:/con";
        }

        contraindications = contraindicationsRepository.findById(id).orElseThrow();
        model.addAttribute("contraindications", contraindications);
        return "contraindications/con-update";
    }

    @PostMapping("/{id}/update")
    public String makerUpd(@PathVariable(value = "id") long id, Model model,
                           @Valid Contraindications contraindications, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "contraindications/con-update";
        }
        contraindicationsRepository.save(contraindications);
        return "redirect:/con";
    }

    @PostMapping("/{id}/del")
    public String makerDelete(@PathVariable(value = "id") long id, Model model){
        Contraindications contraindications = contraindicationsRepository.findById(id).orElseThrow();
        contraindicationsRepository.delete(contraindications);
        return "redirect:/con";
    }
}
