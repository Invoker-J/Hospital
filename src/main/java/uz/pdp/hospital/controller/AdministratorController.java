package uz.pdp.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hospital.entity.*;
import uz.pdp.hospital.entity.enums.Status;
import uz.pdp.hospital.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/administration")
public class AdministratorController {

    private final AdministrationService administrationService;
    private final PatientService patientService;
    private final AdmissionService admissionService;
    private final DoctorService doctorService;
    private final UserService userService;

    @GetMapping
    public String administration(Model model) {
        List<Patient> patients = patientService.findAllOrderBy();
        model.addAttribute("patients", patients);
        return "administration/administration";
    }

//    @GetMapping()
//    public String administration(Model model) {
//        List<Doctor> doctors = doctorService.findAll();
//        model.addAttribute("doctors", doctors);
//        return "administration/administration";
//    }


    @PostMapping
    public String getPatientDetails(@RequestParam("selectedPatientId") int patientId, Model model) {
        List<Patient> patients = patientService.findAllOrderBy();
        Patient selectedPatient = patients.stream()
                .filter(patient -> patient.getId() == patientId)
                .findFirst()
                .orElse(null);

        model.addAttribute("patients", patients);
        List<Admission> admissions = admissionService.findAllUserAdmission(patientId);
        model.addAttribute("admissions", admissions);

        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);

        model.addAttribute("selectedPatient", selectedPatient);
        return "administration/administration";
    }

    @GetMapping("/addPatient")
    public String addPatient() {
        System.out.println("keldi ");
        return "administration/addPatient";
    }


    @PostMapping("/addPatient")
    public String savePatient(@ModelAttribute User user, Model model) {
        System.out.println("user = " + user);
        User save = userService.save(user);
        Patient patient = Patient.builder()
                .user(user)
                .status(Status.REGISTERED)
                .build();
        Patient savedPatient = patientService.save(patient);
        System.out.println("savedPatient = " + savedPatient);
        List<Patient> patients = patientService.findAllOrderBy();
        model.addAttribute("patients", patients);
        return "administration/administration";
    }

    @PostMapping("/admission")
    public String admission(@RequestParam Integer doctorId,
                            @RequestParam Integer patientId,
                            @RequestParam String description,
                            @RequestParam LocalDateTime dateTime,
                            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phone = authentication.getName();
        Administrator administrator =  administrationService.findByUserPhone(phone);
        System.out.println("administrator = " + administrator);

        System.out.println("doctorId = " + doctorId);
        System.out.println("patientId = " + patientId);
        Doctor doctor = doctorService.findById(doctorId).get();
        Patient patient = patientService.findById(patientId).get();
        patient.setStatus(Status.BOOKED);
        patientService.update(patient, patient.getId());

        Admission admission = Admission.builder()
                .patient(patient)
                .doctor(doctor)
                .administrator(administrator)
                .admissionDateTime(dateTime)
                .description(description)
                .build();
        admission.setStatus(Status.BOOKED);

        Admission save = admissionService.save(admission);
        System.out.println("save = " + save);

        List<Patient> patients = patientService.findAllOrderBy();
        List<Admission> admissions = admissionService.findAllUserAdmission(patientId);
        model.addAttribute("admissions", admissions);
        model.addAttribute("patients", patients);
        Patient patient1 = patientService.findById(patientId).get();
        model.addAttribute("selectedPatient", patient1);
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);

        return "redirect:/administration";
    }


    @PostMapping("/admission/enter")
    public String admissionEnter(@RequestParam Integer admissionId) {
//        System.out.println("id = " + id);
        System.out.println("admissionId = " + admissionId);
//
        Optional<Admission> admissionOptional = admissionService.findById(admissionId);
        if (admissionOptional.isEmpty()) {
            return "administration/administration";
        }


        Admission admission = admissionOptional.get();

        List<Admission> admissions = admissionService.findAllByDoctorId(admission.getDoctor().getId());

        for (Admission ad : admissions) {
            if (ad.getPatient().getId().equals(admission.getPatient().getId())) {
                Patient patient = ad.getPatient();
                patient.setStatus(Status.WAITING);
                patientService.update(ad.getPatient(), ad.getPatient().getId());
                return "redirect:/administration";
            }
        }

        admission.setStatus(Status.WAS_LATE);
        admission.setArrivedDateTime(LocalDateTime.now());

        Patient patient = admission.getPatient();
        patient.setStatus(Status.WAITING);
        patientService.update(patient, patient.getId());

        admission.setPatient(patient);
        admissionService.update(admission, admission.getId());

        return "redirect:/administration";
    }

    @PostMapping("/info")
    public String info(@RequestParam Integer admissionId, Model model) {
        Optional<Admission> admissionOptional = admissionService.findById(admissionId);
        if (admissionOptional.isEmpty()) {
            return "redirect:/administration";
        }
        Admission admission = admissionOptional.get();
        Patient patient = admission.getPatient();
        model.addAttribute("admission", admission);
        model.addAttribute("patient", patient);
        return "administration/info";
    }


}
