package uz.pdp.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hospital.entity.Procedure;

public interface ProcedureRepo extends JpaRepository<Procedure, Integer> {
}