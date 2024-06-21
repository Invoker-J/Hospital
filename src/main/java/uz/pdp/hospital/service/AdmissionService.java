package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hospital.entity.Admission;
import uz.pdp.hospital.repo.AdmissionRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdmissionService implements BaseService<Admission, Integer> {
    private final AdmissionRepo admissionRepo;

    @Override
    public Optional<Admission> findById(Integer id) {
        return admissionRepo.findById(id);
    }

    @Override
    public Admission save(Admission admission) {
        return admissionRepo.save(admission);
    }

    @Override
    public void delete(Integer integer) {
        admissionRepo.deleteById(integer);
    }

    @Override
    public List<Admission> findAll() {
        return admissionRepo.findAll();
    }

    @Override
    public Admission update(Admission admission, Integer integer) {
        admission.setId(integer);
        return admissionRepo.save(admission);
    }


    public List<Admission> findAllUserAdmission(Integer id) {
        List<Admission> admissions = findAll();
        List<Admission> userAdmissions = new ArrayList<>();

        for (Admission admission : admissions) {
            if (admission.getPatient().getId().equals(id))
                userAdmissions.add(admission);
        }
        return userAdmissions;
    }


    public List<Admission> findAdmissionByPatientId(int patientId) {
        return admissionRepo.findAdmissionsByPatientId(patientId);
    }

    public List<Admission> findAllByDoctorId(Integer doctorId) {
        return admissionRepo.findAllByDoctorId(doctorId);
    }

    public List<Admission> findAllByPatientId(Integer patientId) {
        return admissionRepo.findAllByPatientId(patientId);
    }
}

