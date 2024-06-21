package uz.pdp.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hospital.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByPhone(String phone);
}