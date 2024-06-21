package uz.pdp.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hospital.entity.Administrator;
import uz.pdp.hospital.entity.Admission;
import uz.pdp.hospital.service.AdmissionService;
import uz.pdp.hospital.service.PatientService;

@RestController
@RequestMapping("/administration")
@RequiredArgsConstructor
public class AdministratorResController {
    private final PatientService patientService;
    private final AdmissionService admissionService;

    @GetMapping("/get-patients")
    public ResponseEntity<?> admissionEnter() {

        return ResponseEntity.ok().body(patientService.findAll());

    }

    @GetMapping("/get-history/{patientId}")
    public ResponseEntity<?> admissionHistory(@PathVariable Integer patientId) {
//        admissionService.findAdmissionByPatientId(patientId);

        return ResponseEntity.ok().body(admissionService.findAdmissionByPatientId(patientId));
    }
}
