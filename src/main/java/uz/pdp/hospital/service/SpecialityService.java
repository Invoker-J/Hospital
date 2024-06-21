package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hospital.entity.Speciality;
import uz.pdp.hospital.repo.SpecialityRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialityService implements BaseService<Speciality, Integer> {
    private final SpecialityRepo specialityRepo;
    @Override
    public Optional<Speciality> findById(Integer id) {
        return specialityRepo.findById(id);
    }

    @Override
    public Speciality save(Speciality speciality) {
        return specialityRepo.save(speciality);
    }

    @Override
    public void delete(Integer integer) {
        specialityRepo.deleteById(integer);
    }

    @Override
    public List<Speciality> findAll() {
        return specialityRepo.findAll();
    }

    @Override
    public Speciality update(Speciality speciality, Integer integer) {
        speciality.setId(integer);
        return specialityRepo.save(speciality);
    }
}
