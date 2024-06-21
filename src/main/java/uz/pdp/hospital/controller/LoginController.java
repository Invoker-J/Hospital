package uz.pdp.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@ComponentScan("uz.pdp")
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    @GetMapping
    public String login()  {
        return "login";
    }


    @GetMapping("/test")
    public String test(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("principal = " + principal);
        return "redirect:/";
    }
}
