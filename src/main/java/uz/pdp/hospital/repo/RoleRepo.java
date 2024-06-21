package uz.pdp.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.hospital.entity.Role;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    @Query(value = "select * from roles where role = :name ",nativeQuery = true)
    Role findByName(String name);

    @Query(value = "select r.* from roles r join users_role ur on ur.role_id = r.id where user_id =?1;", nativeQuery = true)
    List<Role> findRolesByUserId(int userId);
}