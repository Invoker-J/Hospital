package uz.pdp.hospital.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.hospital.entity.*;
import uz.pdp.hospital.entity.enums.RoleName;
import uz.pdp.hospital.entity.enums.Status;
import uz.pdp.hospital.service.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RunnerClass implements CommandLineRunner {
    private final UserService userService;
    private final SpecialityService specialityService;
    private final RoomService roomService;
    private final RoleService roleService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AdministrationService administrationService;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    String ddl;

    @Override
    public void run(String... args) throws Exception {
        if (ddl.equals("create")) {
//            createDoctors();
//            createPatients();
            createAdministrations();
            createSupperAdmin();
        }

    }

    private void    createSupperAdmin() {
        Role role = Role.builder().role(RoleName.ROLE_SUPER_ADMIN).build();
        roleService.save(role);
        User user = User.builder()
                .firstname("Javohir")
                .lastname("SHukurulayev")
                .phone("+998903231604")
                .password("0505")
                .role(List.of(role))
                .build();
        userService.save(user);
    }

    private void createAdministrations() {

        Role role = Role.builder().role(RoleName.ROLE_ADMINISTRATION).build();
        roleService.save(role);

        for (int i = 90; i <= 94; i++) {

            User user = User.builder()
                    .firstname("Hikmatoy-" + i)
                    .lastname("Nortoyova")
                    .phone("+9989012345" + i)
                    .password(("1"))
                    .role(List.of(role)).build();
            userService.save(user);

            Administrator administration = Administrator.builder().user(user).build();
            administrationService.save(administration);
        }
    }

    private void createPatients() {

//        Status status = Status.builder().status(PatientStatus.NOT_REGISTERED).build();
//        statusService.save(status);

        Role role = Role.builder().role(RoleName.ROLE_PATIENT).build();
        roleService.save(role);

        for (int i = 6; i <= 9; i++) {

            User user = User.builder()
                    .firstname("Nusrat-" + i)
                    .lastname("Hikmatov")
                    .phone("+99890123456" + i)
                    .password("1")
                    .role(List.of(role)).build();
            userService.save(user);

            Patient patient = Patient.builder().user(user).status(Status.REGISTERED).build();
            patientService.save(patient);
        }

    }

    private void createDoctors() {

        Role role = Role.builder().role(RoleName.ROLE_DOCTOR).build();
        roleService.save(role);

        for (int i = 1; i <= 5; i++) {

            User user = User.builder()
                    .firstname("Eshmat-" + i)
                    .lastname("Toshmatov-" + i)
                    .phone("+99890123456" + i)
                    .password("1")
                    .role(List.of(role)).build();
            userService.save(user);

            Speciality speciality = Speciality.builder().name("speciality-" + i).build();
            specialityService.save(speciality);

            Room room = Room.builder().name("room-" + i).build();
            roomService.save(room);


            Doctor doctor = Doctor.builder().user(user).speciality(speciality).room(room).build();
            doctorService.save(doctor);
        }
    }

}
