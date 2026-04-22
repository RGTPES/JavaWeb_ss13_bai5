package org.example.javaweb_ss13_bai5.controller;

import org.example.javaweb_ss13_bai5.dto.PrescriptionDetailForm;
import org.example.javaweb_ss13_bai5.dto.PrescriptionForm;
import org.example.javaweb_ss13_bai5.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping
    public String list(@RequestParam(value = "patientCode", required = false) String patientCode, Model model) {
        model.addAttribute("prescriptions", prescriptionService.searchByPatientCode(patientCode));
        model.addAttribute("patientCode", patientCode);
        return "prescription/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        PrescriptionForm form = new PrescriptionForm();
        if (form.getDetails().isEmpty()) {
            form.getDetails().add(new PrescriptionDetailForm());
        }
        model.addAttribute("form", form);
        return "prescription/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("form") PrescriptionForm form, Model model) {
        List<String> errors = prescriptionService.validate(form);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "prescription/create";
        }

        prescriptionService.save(form);
        return "redirect:/prescriptions";
    }

    @PostMapping("/create/add-row")
    public String addRow(@ModelAttribute("form") PrescriptionForm form, Model model) {
        form.getDetails().add(new PrescriptionDetailForm());
        model.addAttribute("form", form);
        return "prescription/create";
    }
}