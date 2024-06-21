package uz.pdp.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hospital.dto.ProcedureRequest;
import uz.pdp.hospital.entity.Admission;
import uz.pdp.hospital.entity.Doctor;
import uz.pdp.hospital.entity.Patient;
import uz.pdp.hospital.entity.Procedure;
import uz.pdp.hospital.entity.enums.Status;
import uz.pdp.hospital.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.pdp.hospital.entity.enums.Status.IN_RECEPTION;
import static uz.pdp.hospital.entity.enums.Status.WAITING;

@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final UserService userService;
    private final AdmissionService admissionService;
    private final ProcedureService procedureService;

    @GetMapping
    public String doctor(Model model) {
        Doctor doctor = getDoctor();
        if (doctor == null) return "redirect:/login";
        System.out.println("doctor = " + doctor);

        List<Admission> admissions = admissionService.findAllByDoctorId(doctor.getId());

        model.addAttribute("admissions", admissions);

        List<Patient> patients = findAllByAdmission(admissions);
        model.addAttribute("patients", patients);
        model.addAttribute("selectedPatient", null);

        return "doctor/doctor";
    }

    private Doctor getDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        String phone = authentication.getName();
        Optional<Doctor> optionalDoctor = doctorService.findByPhone(phone);
        if (optionalDoctor.isEmpty()) {
            return null;
        }
        Doctor doctor = optionalDoctor.get();
        return doctor;
    }

    private List<Patient> findAllByAdmission(List<Admission> admissions) {
        List<Patient> patients = new ArrayList<>();
        admissions.forEach(admission -> {
            if (admission.getPatient() != null && (admission.getPatient().getStatus().equals(WAITING) || admission.getPatient().getStatus().equals(IN_RECEPTION))) {
                Patient patient = admission.getPatient();
                patients.add(patient);
            }
        });
        return patients;
    }

    @PostMapping
    public String doctorSubmit(@RequestParam("selectedPatientId") Integer patientId, Model model) {
        Optional<Patient> optionalPatient = patientService.findById(patientId);
        if (optionalPatient.isEmpty()) {
            return "redirect:/doctor";
        }
        Patient patient = optionalPatient.get();
        patient.setStatus(IN_RECEPTION);
        patientService.update(patient, patientId);

        Doctor doctor = getDoctor();

        List<Admission> admissions = admissionService.findAllByDoctorId(doctor.getId());
        List<Patient> patients = findAllByAdmission(admissions);

        model.addAttribute("selectedPatient", patient);
        model.addAttribute("patients", patients);
        return "doctor/doctor";
    }

    @PostMapping("/saveProcedure")
    public String saveProcedure(@RequestBody ProcedureRequest procedureRequest, Model model) {
        List<Procedure> procedures = procedureRequest.getProcedures();

        for (Procedure procedure : procedures) {
            System.out.println("procedure = " + procedure);
        }
        procedureService.saveAll(procedures);
        Integer patientId = procedureRequest.getPatientId();
        Optional<Patient> optionalPatient = patientService.findById(patientId);
        if (optionalPatient.isEmpty()) {
            return "redirect:/doctor";
        }
        Patient patient = optionalPatient.get();

        Doctor doctor = getDoctor();
        List<Admission> admissions = admissionService.findAllByDoctorId(doctor.getId());

        for (Admission admission : admissions) {
            if (admission.getPatient().getId().equals(patientId)&&admission.getPatient().getStatus().equals(IN_RECEPTION)) {
                admission.setStatus(Status.COMPLETED);
                admission.setProcedures(procedures);
                admissionService.update(admission, admission.getId());
            }
        }

        patient.setStatus(Status.INSPECTED);

        patientService.update(patient, patientId);
        System.out.println("Bo'ldi");


        List<Patient> patients = findAllByAdmission(admissions);


        model.addAttribute("patients", patients);
        model.addAttribute("selectedPatient", null);

        return "doctor/doctor";
    }
}