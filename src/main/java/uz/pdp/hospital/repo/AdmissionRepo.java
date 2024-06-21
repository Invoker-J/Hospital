package uz.pdp.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.hospital.entity.Admission;

import java.util.List;

public interface AdmissionRepo extends JpaRepository<Admission, Integer> {
    @Query(value = "select ad.* from admission ad where ad.patient_id = ?1", nativeQuery = true)
    List<Admission> findAdmissionsByPatientId(int patientId);

    @Query(value = "select ad.* from admission ad inner join patient p on ad.patient_id = p.id where ad.doctor_id = ?1 and ad.status = 'WAITING' or ad.status = 'WAS_LATE'", nativeQuery = true)
    List<Admission> findAllByDoctorId(Integer doctorId);

    List<Admission> findAllByPatientId(Integer patientId);

}