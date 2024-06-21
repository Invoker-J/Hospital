package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hospital.entity.Doctor;
import uz.pdp.hospital.repo.DoctorRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService implements BaseService<Doctor, Integer> {
    private final DoctorRepo doctorRepo;

    @Override
    public Optional<Doctor> findById(Integer id) {
        return doctorRepo.findById(id);
    }

    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    @Override
    public void delete(Integer integer) {
        doctorRepo.deleteById(integer);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepo.findAll();
    }

    @Override
    public Doctor update(Doctor doctor, Integer integer) {
        doctor.setId(integer);
        return doctorRepo.save(doctor);
    }

    public Optional<Doctor> findByPhone(String name) {
        return doctorRepo.findByPhone(name);
    }

}
