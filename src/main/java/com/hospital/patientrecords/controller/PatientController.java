package com.hospital.patientrecords.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;

import com.hospital.patientrecords.entity.Patient;
import com.hospital.patientrecords.service.PatientService;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

   
    @GetMapping
    public String viewPatients(
            @RequestParam(required = false) String keyword,
            Model model) {

        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("patients",
                    patientService.searchPatient(keyword));
        } else {
            model.addAttribute("patients",
                    patientService.getAllPatients());
        }

        model.addAttribute("keyword", keyword);
        return "patients";
    }

  
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient-form";
    }

   
    @PostMapping("/save")
    public String savePatient(
            @Valid @ModelAttribute("patient") Patient patient,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "patient-form";
        }

        patientService.addPatient(patient);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Patient added successfully!");

        return "redirect:/patients";
    }

   
    @GetMapping("/edit/{id}")
    public String editPatient(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("patient",
                patientService.getPatientById(id));

        return "patient-form";
    }

 // Update patient with validation and role-based security
    @PostMapping("/update/{id}")
    public String updatePatient(
            @PathVariable Long id,
            @Valid @ModelAttribute("patient") Patient patient,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Authentication authentication) {

        if (result.hasErrors()) {
            return "patient-form";
        }

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        System.out.println("Logged-in User: " + authentication.getName());

        System.out.println("User Authorities: " + authentication.getAuthorities());

        System.out.println("Is Admin: " + isAdmin);

        patientService.updatePatient(id, patient, isAdmin);
        redirectAttributes.addFlashAttribute(
                "successMessage", "Patient updated successfully!");

        return "redirect:/patients";
    }

  
    @PostMapping("/delete/{id}")
    public String deletePatient(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        patientService.deletePatient(id);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Patient deleted successfully!");

        return "redirect:/patients";
    }

 
    @GetMapping("/view/{id}")
    public String viewPatientDetails(
            @PathVariable Long id,
            Model model,
            Authentication authentication) {

        Patient patient = patientService.getPatientById(id);

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                    authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            patient.setMedicalHistory(null);
        }

        model.addAttribute("patient", patient);

        return "patient-details";
    }
}