package com.example.medrating.controllers;

import com.example.medrating.models.Defect;
import com.example.medrating.repository.DefectRepository;
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
@RequestMapping("/defect")
public class DefectController {
    @Autowired
    DefectRepository defectRepository;

    @GetMapping("")
    public String allMaker(Model model)
    {
        Iterable<Defect> defects = defectRepository.findAll();
        model.addAttribute("defect", defects);
        return "defect/def-main";
    }

    @GetMapping("/add")
    public String addMaker(Model model, Defect defect){
        return "defect/def-add";
    }

    @PostMapping("/add")
    public String PostaddMaker(@Valid Defect defect, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "defect/def-add";
        }
        defectRepository.save(defect);
        return "redirect:/defect";
    }

    @GetMapping("/{id}")
    public String MakerDetails(@PathVariable(value = "id") long id, Model model){
        Defect defect = defectRepository.findById(id).orElseThrow();
        model.addAttribute("defect", defect);

        if (!defectRepository.existsById(id)){
            return "redirect:/defect";
        }
        return "defect/def-details";
    }


    @GetMapping("/{id}/update")
    public String makerEdit(@PathVariable(value = "id") long id, Model model, Defect defect){
        if (!defectRepository.existsById(id)){
            return "redirect:/defect";
        }

        defect = defectRepository.findById(id).orElseThrow();
        model.addAttribute("defect", defect);
        return "defect/def-update";
    }

    @PostMapping("/{id}/update")
    public String makerUpd(@PathVariable(value = "id") long id, Model model,
                           @Valid Defect defect, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "defect/def-update";
        }
        defectRepository.save(defect);
        return "redirect:/defect";
    }

    @PostMapping("/{id}/del")
    public String makerDelete(@PathVariable(value = "id") long id, Model model){
        Defect defect = defectRepository.findById(id).orElseThrow();
        defectRepository.delete(defect);
        return "redirect:/defect";
    }
}
