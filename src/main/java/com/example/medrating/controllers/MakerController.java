package com.example.medrating.controllers;

import com.example.medrating.models.Maker;
import com.example.medrating.models.Preporat;
import com.example.medrating.repository.MakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/maker")
public class MakerController {
    @Autowired
    MakerRepository makerRepository;

    @GetMapping("")
    public String allMaker(Model model)
    {
        Iterable<Maker> makers = makerRepository.findAll();
        model.addAttribute("maker", makers);
        return "maker/mak-main";
    }

    @GetMapping("/add")
    public String addMaker(Model model, Maker maker){
        return "maker/mak-add";
    }

    @PostMapping("/add")
    public String PostaddMaker(@Valid Maker maker, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "maker/mak-add";
        }
        makerRepository.save(maker);
        return "redirect:/maker";
    }

    @GetMapping("/{id}")
    public String MakerDetails(@PathVariable(value = "id") long id, Model model){
        Maker maker = makerRepository.findById(id).orElseThrow();
        model.addAttribute("maker", maker);

        if (!makerRepository.existsById(id)){
            return "redirect:/maker";
        }
        return "maker/mak-details";
    }


    @GetMapping("/{id}/update")
    public String makerEdit(@PathVariable(value = "id") long id, Model model, Maker maker){
        if (!makerRepository.existsById(id)){
            return "redirect:/maker";
        }

        maker = makerRepository.findById(id).orElseThrow();
        model.addAttribute("maker", maker);
        return "maker/mak-update";
    }

    @PostMapping("/{id}/update")
    public String makerUpd(@PathVariable(value = "id") long id, Model model,
                               @Valid Maker maker, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "maker/mak-update";
        }
        makerRepository.save(maker);
        return "redirect:/maker";
    }

    @PostMapping("/{id}/del")
    public String makerDelete(@PathVariable(value = "id") long id, Model model){
        Maker maker = makerRepository.findById(id).orElseThrow();
        makerRepository.delete(maker);
        return "redirect:/maker";
    }
}
