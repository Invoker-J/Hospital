package uz.pdp.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.hospital.entity.Doctor;

import java.util.Optional;

public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    @Query(value = "select d.* from doctor d inner join users u on u.id = d.user_id where u.phone = ?1",nativeQuery = true)
    Optional<Doctor> findByPhone(String name);
}