package com.example.medrating.controllers;

import com.example.medrating.models.Effect;
import com.example.medrating.models.Maker;
import com.example.medrating.repository.EffectRepository;
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
@RequestMapping("/effect")
public class EffectController {
    @Autowired
    EffectRepository effectRepository;

    @GetMapping("")
    public String allMaker(Model model)
    {
        Iterable<Effect> effects = effectRepository.findAll();
        model.addAttribute("effect", effects);
        return "effect/eff-main";
    }

    @GetMapping("/add")
    public String addMaker(Model model, Effect effect){
        return "effect/eff-add";
    }

    @PostMapping("/add")
    public String PostaddMaker(@Valid Effect effect, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "effect/eff-add";
        }
        effectRepository.save(effect);
        return "redirect:/effect";
    }

    @GetMapping("/{id}")
    public String MakerDetails(@PathVariable(value = "id") long id, Model model){
        Effect effect = effectRepository.findById(id).orElseThrow();
        model.addAttribute("effect", effect);

        if (!effectRepository.existsById(id)){
            return "redirect:/effect";
        }
        return "effect/eff-details";
    }


    @GetMapping("/{id}/update")
    public String makerEdit(@PathVariable(value = "id") long id, Model model, Effect effect){
        if (!effectRepository.existsById(id)){
            return "redirect:/effect";
        }

        effect = effectRepository.findById(id).orElseThrow();
        model.addAttribute("effect", effect);
        return "effect/eff-update";
    }

    @PostMapping("/{id}/update")
    public String makerUpd(@PathVariable(value = "id") long id, Model model,
                           @Valid Effect effect, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "effect/eff-update";
        }
        effectRepository.save(effect);
        return "redirect:/effect";
    }

    @PostMapping("/{id}/del")
    public String makerDelete(@PathVariable(value = "id") long id, Model model){
        Effect effect = effectRepository.findById(id).orElseThrow();
        effectRepository.delete(effect);
        return "redirect:/effect";
    }
}
