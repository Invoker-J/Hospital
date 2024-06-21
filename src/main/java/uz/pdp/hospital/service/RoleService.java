package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hospital.entity.Role;
import uz.pdp.hospital.entity.enums.RoleName;
import uz.pdp.hospital.repo.RoleRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements BaseService<Role, Integer> {
    private final RoleRepo roleRepo;
    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepo.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void delete(Integer integer) {
        roleRepo.deleteById(integer);
    }

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role update(Role role, Integer integer) {
        role.setId(integer);
        return roleRepo.save(role);
    }

    public Role findByName(RoleName roleName) {
        return roleRepo.findByName(roleName.name());
    }
}
