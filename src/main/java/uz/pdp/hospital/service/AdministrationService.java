package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hospital.entity.Administrator;
import uz.pdp.hospital.repo.AdministratorRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministrationService implements BaseService<Administrator, Integer> {
    private final AdministratorRepo administratorRepo;
    @Override
    public Optional<Administrator> findById(Integer id) {
        return administratorRepo.findById(id);
    }

    @Override
    public Administrator save(Administrator administrator) {
        return administratorRepo.save(administrator);
    }

    @Override
    public void delete(Integer integer) {
        administratorRepo.deleteById(integer);
    }

    @Override
    public List<Administrator> findAll() {
        return administratorRepo.findAll();
    }

    @Override
    public Administrator update(Administrator administrator, Integer integer) {
        administrator.setId(integer);
        return administratorRepo.save(administrator);
    }

    public Administrator findByUserPhone(String phone) {
        Optional<Administrator> administratorByUserPhone = administratorRepo.findAdministratorByUserPhone(phone);
        return administratorByUserPhone.orElse(null);
    }
}
