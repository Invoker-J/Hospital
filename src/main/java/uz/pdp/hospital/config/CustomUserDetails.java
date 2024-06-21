package uz.pdp.hospital.config;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.hospital.entity.Role;
import uz.pdp.hospital.entity.User;
import uz.pdp.hospital.repo.RoleRepo;
import uz.pdp.hospital.repo.UserRepo;

import java.util.List;
import java.util.Optional;
//@Component
@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByPhone(username);
        if (optionalUser.isPresent()) {

        User user = optionalUser.get();

            List<Role> roles = roleRepository.findRolesByUserId(user.getId());
            System.out.println("loadUserByUsername !!!");
            roles.forEach(System.out::println);
            System.out.println("loadUserByUsername !!! finished");
            user.setRole(roles);
            userRepository.save(user);
            return user;
        }

        return null;
    }
}
