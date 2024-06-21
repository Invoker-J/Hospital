package uz.pdp.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hospital.entity.Speciality;

public interface SpecialityRepo extends JpaRepository<Speciality, Integer> {
}