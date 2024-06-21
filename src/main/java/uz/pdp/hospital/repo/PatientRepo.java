package uz.pdp.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.hospital.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepo extends JpaRepository<Patient, Integer> {
    @Query(value = "select * from patient order by id desc ",nativeQuery = true)
    List<Patient> findAllOrderById();

    @Query(value = "select p.* from patient p inner join public.users u on u.id = p.user_id where u.phone = ?1",nativeQuery = true)
    Optional<Patient> findPatientByPhone(String phone);
}