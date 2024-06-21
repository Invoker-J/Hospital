package uz.pdp.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.hospital.entity.Admission;
import uz.pdp.hospital.entity.Patient;
import uz.pdp.hospital.service.AdmissionService;
import uz.pdp.hospital.service.PatientService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final AdmissionService admissionService;

    @GetMapping
    public String patient(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phone = authentication.getName();
        Patient patient = patientService.findByPhone(phone);
        System.out.println("patient = " + patient);

        List<Admission> admissions = admissionService.findAllByPatientId(patient.getId());

        for (Admission admission : admissions) {
            System.out.println("admission = " + admission);
        }
        model.addAttribute("patient", patient);
        model.addAttribute("admissions", admissions);
        return "patient/patient";
    }


    @PostMapping("/info")
    public String info(@RequestParam Integer admissionId, Model model) {
        Optional<Admission> admissionOptional = admissionService.findById(admissionId);
        if (admissionOptional.isEmpty()) {
            return "redirect:/patient";
        }
        Admission admission = admissionOptional.get();
        Patient patient = admission.getPatient();
        model.addAttribute("admission", admission);
        model.addAttribute("check", "check");
        return "patient/info";
    }
}
