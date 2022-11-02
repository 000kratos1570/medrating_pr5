package com.example.medrating.controllers;

import com.example.medrating.models.*;
import com.example.medrating.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PreporatController {
    @Autowired
    PreporatRepository preporatRepository;
    @Autowired
    MakerRepository makerRepository;
    @Autowired
    ContraindicationsRepository contraindicationsRepository;
    @Autowired
    DefectRepository defectRepository;
    @Autowired
    EffectRepository effectRepository;
    @Autowired
    StarRepository starRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/preporats")
    public String allPreporats(Model model)
    {
        Iterable<Preporat> preporats = preporatRepository.findAll();
        model.addAttribute("preporat", preporats);
        return "preporats/prep-main";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/preporats/add")
    public String addPreporat(Model model, Preporat preporat){
        Iterable<Maker> makers = makerRepository.findAll();
        Iterable<Contraindications> contraindications = contraindicationsRepository.findAll();
        Iterable<Defect> defects = defectRepository.findAll();
        Iterable<Effect> effects = effectRepository.findAll();

        model.addAttribute("maker", makers);
        model.addAttribute("contraindications", contraindications);
        model.addAttribute("defect", defects);
        model.addAttribute("effect", effects);

        return "preporats/prep-add";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/preporats/add")
    public String PostaddPreporat(@Valid Preporat preporat, BindingResult bindingResult, Model model,
                               @RequestParam Long maker/*,
                               @RequestParam Long contraindications,
                               @RequestParam Long defect,
                               @RequestParam Long effect*/){
        if (bindingResult.hasErrors()){
            return "preporats/prep-add";
        }
        Maker makers = makerRepository.findById(maker).orElseThrow();
//        Contraindications contraindications1 = contraindicationsRepository.findById(contraindications).orElseThrow();
//        List<Contraindications> listContraindications = new ArrayList<>();
//        listContraindications.add(contraindications1);
//        Defect defect1 = defectRepository.findById(defect).orElseThrow();
//        List<Defect> listDefect = new ArrayList<>();
//        listDefect.add(defect1);
//        Effect effect1 = effectRepository.findById(effect).orElseThrow();
//        List<Effect> listEffect = new ArrayList<>();
//        listEffect.add(effect1);
        preporat.setMakers(makers);
//        preporat.setContraindications(listContraindications);
//        preporat.setDefects(listDefect);
//        preporat.setEffects(listEffect);
        Star star0 = new Star();
        star0.setStarName(0.0);
        preporat.setStars(star0);

        preporatRepository.save(preporat);
        return "redirect:/preporats";
    }

    @GetMapping("/preporats/{id}")
    public String preporatsDetails(@PathVariable(value = "id") long id, Model model){
        Preporat preporat = preporatRepository.findById(id).orElseThrow();
        model.addAttribute("preporat", preporat);

        Iterable<Maker> makers = makerRepository.findAll();
        model.addAttribute("maker", makers);
        Iterable<Star> stars = starRepository.findAll();
        model.addAttribute("star", stars);

        if (!preporatRepository.existsById(id)){
            return "redirect:/preporats";
        }
        return "preporats/prep-details";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/preporats/{id}/update")
    public String preporatsEdit(@PathVariable(value = "id") long id, Model model, Preporat preporat){
        if (!preporatRepository.existsById(id)){
            return "redirect:/preporats";
        }
        Iterable<Maker> makers = makerRepository.findAll();

        model.addAttribute("maker", makers);
        preporat = preporatRepository.findById(id).orElseThrow();
        model.addAttribute("preporat", preporat);
        return "preporats/prep-update";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/preporats/{id}/update")
    public String preporatsUpd(@PathVariable(value = "id") long id, Model model,
                           @Valid Preporat preporat, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "preporats/prep-update";
        }
        preporatRepository.save(preporat);
        return "redirect:/preporats";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/preporats/{id}/del")
    public String preporatDelete(@PathVariable(value = "id") long id, Model model){
        Preporat preporat = preporatRepository.findById(id).orElseThrow();
        preporatRepository.delete(preporat);
        return "redirect:/preporats";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/preporats/con")
    public String preporatCon(Model model){
        Iterable<Preporat> preporats = preporatRepository.findAll();
        Iterable<Contraindications> contraindications = contraindicationsRepository.findAll();
        Iterable<Defect> defects = defectRepository.findAll();
        Iterable<Effect> effects = effectRepository.findAll();
        model.addAttribute("preporat", preporats);
        model.addAttribute("contraindication", contraindications);
        model.addAttribute("defect", defects);
        model.addAttribute("effect", effects);
        return "preporats/prep-con";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/preporats/con/contr")
    public String preporatsConAddContr(@RequestParam Long preporat,
                              @RequestParam Long contraindications,
                              Model model){
        Preporat preporat1 = preporatRepository.findById(preporat).orElseThrow();
        Contraindications contraindications1 = contraindicationsRepository.findById(contraindications).orElseThrow();
        preporat1.getContraindications().add(contraindications1);
        preporatRepository.save(preporat1);
        return "redirect:/preporats/con";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/preporats/con/def")
    public String preporatsConAddDef(@RequestParam Long preporat,
                              @RequestParam Long defect,
                              Model model){
        Preporat preporat1 = preporatRepository.findById(preporat).orElseThrow();
        Defect defect1 = defectRepository.findById(defect).orElseThrow();
        preporat1.getDefects().add(defect1);
        preporatRepository.save(preporat1);
        return "redirect:/preporats/con";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/preporats/con/eff")
    public String preporatsConAddEff(@RequestParam Long preporat,
                                     @RequestParam Long effect,
                                     Model model){
        Preporat preporat1 = preporatRepository.findById(preporat).orElseThrow();
        Effect effect1 = effectRepository.findById(effect).orElseThrow();
        preporat1.getEffects().add(effect1);
        preporatRepository.save(preporat1);
        return "redirect:/preporats/con";
    }
}
