package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hospital.entity.Patient;
import uz.pdp.hospital.repo.PatientRepo;
import uz.pdp.hospital.repo.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService implements BaseService<Patient, Integer> {
    private final PatientRepo patientRepo;
    private final UserRepo userRepo;

    @Override
    public Optional<Patient> findById(Integer id) {
        return patientRepo.findById(id);
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepo.save(patient);
    }

    @Override
    public void delete(Integer integer) {
        patientRepo.deleteById(integer);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepo.findAll();
    }

    @Override
    public Patient update(Patient patient, Integer integer) {
        patient.setId(integer);
        return patientRepo.save(patient);
    }

    public List<Patient> findAllOrderBy() {
        return patientRepo.findAllOrderById();
    }

    public Patient findByPhone(String phone) {
        Optional<Patient> patientByPhone = patientRepo.findPatientByPhone(phone);
        return patientByPhone.orElse(null);

    }
}
