package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.hospital.entity.Doctor;
import uz.pdp.hospital.entity.User;
import uz.pdp.hospital.repo.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<User, Integer> {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final DoctorService doctorService;

    @Override
    public Optional<User> findById(Integer id) {
        return userRepo.findById(id);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void delete(Integer integer) {
        userRepo.deleteById(integer);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User update(User user, Integer integer) {
        user.setId(integer);
        return userRepo.save(user);
    }


}
