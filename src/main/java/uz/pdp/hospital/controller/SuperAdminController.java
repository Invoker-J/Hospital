package uz.pdp.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hospital.entity.*;
import uz.pdp.hospital.entity.enums.RoleName;
import uz.pdp.hospital.service.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class SuperAdminController {
    private final SpecialityService specialityService;
    private final PatientService patientService;
    private final UserService userService;
    private final RoomService roomService;
    private final DoctorService doctorService;
    private final RoleService roleService;

    //    private final
    @GetMapping
    public String superAdmin() {
        return "/admin/super_admin_page";
    }

    @GetMapping("/specialities")
    public String specialities(Model model) {
        List<Speciality> specialities = specialityService.findAll();
        model.addAttribute("specialities", specialities);
        return "/admin/crud-speciality";
    }

    @GetMapping("/patients")
    public String patients(Model model) {
        List<Patient> patients = patientService.findAll();
        model.addAttribute("patients", patients);
        return "/admin/crud-patient";
    }


    @GetMapping("/doctors")
    public String doctors(Model model) {
        List<Speciality> specialities = specialityService.findAll();
        model.addAttribute("specialities", specialities);
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);

        return "/admin/crud-doctor";
    }


    @PostMapping("/specialities")
    public String addSpeciality(@ModelAttribute Speciality speciality) {
        Speciality save = specialityService.save(speciality);
        System.out.println("save specialities= " + save);
        return "redirect:/admin/specialities";
    }

    @PostMapping("/patients")
    public String addPatient(@ModelAttribute User user) {
        Role role = roleService.findByName(RoleName.ROLE_PATIENT);
        user.setRole(List.of(role));
        user.setPassword("1");

        User save = userService.save(user);

        Patient patient = new Patient();
        patient.setUser(save);
        System.out.println("patient = " + patient);
        Patient patient1 = patientService.save(patient);
        System.out.println("save patient = " + patient1);
        return "redirect:/admin/patients";
    }

    @PostMapping("/doctors")
    public String addDoctor(@ModelAttribute User user,
                            @RequestParam Integer speciality,
                            @RequestParam String room) {
        user.setPassword("1");
        User save = userService.save(user);
        Optional<Speciality> optionalSpeciality = specialityService.findById(speciality);
        if (optionalSpeciality.isEmpty()) {
            return "redirect:/admin/specialities";
        }


        Speciality saveSpeciality = optionalSpeciality.get();

        Role role = roleService.findByName(RoleName.ROLE_DOCTOR);

        save.setRole(List.of(role));

        Doctor doctor = Doctor.builder()
                .user(save)
                .speciality(saveSpeciality)
                .build();


        Room newRoom = hasRoom(room);
        if (newRoom != null) {
            doctor.setRoom(newRoom);
        } else {
            Room room1 = new Room();
            room1.setName(room);
            Room save2 = roomService.save(room1);
            doctor.setRoom(save2);
        }

        Doctor savedDoctor = doctorService.save(doctor);
        System.out.println("savedDoctor = " + savedDoctor);
        return "redirect:/admin/doctors";
    }

    @PostMapping("/patients/delete")
    public String deletePatient(@RequestParam Integer id) {
        patientService.delete(id);
        return "redirect:/admin/patients";
    }

    @PostMapping("/patients/edit")
    public String editPatient(@RequestParam Integer id, Model model) {
        Optional<Patient> byId = patientService.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/admin/patients";
        }
        Patient patient = byId.get();
        model.addAttribute("patient", patient);
//        System.out.println("patient = " + patient);
        return "/admin/edit_patient";
    }

    @PostMapping("/patients/update")
    public String updatePatient(@ModelAttribute User user, @RequestParam Integer id) {
        User update = userService.update(user, id);
//        System.out.println("update.toString() = " + update.toString());
        return "redirect:/admin/patients";
    }


    @PostMapping("/specialities/edit")
    public String editSpecialities(@RequestParam Integer id, Model model) {
        Optional<Speciality> optionalSpeciality = specialityService.findById(id);
        if (optionalSpeciality.isEmpty()) {
            return "redirect:/admin/specialities";
        }
        Speciality saveSpeciality = optionalSpeciality.get();
        model.addAttribute("speciality", saveSpeciality);
        return "/admin/edit_speciality";
    }

    @PostMapping("/specialities/update")
    public String updateSpeciality(@ModelAttribute Speciality speciality, @RequestParam Integer id) {
        specialityService.update(speciality, id);
        return "redirect:/admin/specialities";
    }

    @PostMapping("/specialities/delete")
    public String deleteSpeciality(@RequestParam Integer id) {
        specialityService.delete(id);
        return "redirect:/admin/specialities";
    }

    @PostMapping("/doctors/edit")
    public String editDoctors(@RequestParam Integer id, Model model) {
        Optional<Doctor> optionalDoctor = doctorService.findById(id);
        List<Speciality> specialities = specialityService.findAll();
        model.addAttribute("specialities", specialities);
        if (optionalDoctor.isEmpty()) {
            return "redirect:/admin/doctors";
        }
        Doctor saveDoctor = optionalDoctor.get();
        model.addAttribute("doctor", saveDoctor);
        return "/admin/edit_doctor";
    }

    @PostMapping("/doctors/update")
    public String updateSpeciality(@ModelAttribute User user,
                                   @RequestParam Integer userId,
                                   @RequestParam Integer doctorId,
                                   @RequestParam Speciality speciality,
                                   @RequestParam String room
    ) {
        System.out.println("speciality.toString() = " + speciality.getId());
        System.out.println("speciality.toString() = " + speciality.getName());
        user.setId(userId);
        User updateUser = userService.update(user, userId);
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctorService.update(doctor, doctorId);
        doctor.setSpeciality(speciality);

        Room inDBRoom = hasRoom(room);
        if (inDBRoom != null) {
            doctor.setRoom(inDBRoom);
        } else {
            Room newRoom = new Room();
            newRoom.setName(room);
            Room save = roomService.save(newRoom);
            doctor.setRoom(save);
        }

        doctor.setUser(updateUser);

        Doctor update = doctorService.update(doctor, doctorId);

        System.out.println("user = " + user);
        System.out.println("doctorId = " + doctorId);
        System.out.println("userId = " + userId);

        System.out.println("update doctor = " + update);
        return "redirect:/admin/doctors";
    }

    @PostMapping("/doctors/delete")
    public String deleteDoctor(@RequestParam Integer id) {
        doctorService.delete(id);
        return "redirect:/admin/doctors";
    }

    private Room hasRoom(String room) {
        List<Room> all = roomService.findAll();
        for (Room room1 : all) {
            if (room1.getName().equalsIgnoreCase(room)) {
                return room1;
            }
        }

        return null;
    }
}
