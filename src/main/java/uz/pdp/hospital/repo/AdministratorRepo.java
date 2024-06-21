package uz.pdp.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hospital.entity.Administrator;

import java.util.Optional;

public interface AdministratorRepo extends JpaRepository<Administrator, Integer> {
    Optional<Administrator> findAdministratorByUserPhone(String phone);
}