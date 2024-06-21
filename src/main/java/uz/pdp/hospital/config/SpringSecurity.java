package uz.pdp.hospital.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import uz.pdp.hospital.entity.enums.RoleName;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurity {
    private final CustomUserDetails customUserDetails;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.userDetailsService(customUserDetails);

        http.authorizeHttpRequests(manager -> {
            manager
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/administration/**").hasRole("ADMINISTRATION")
                    .requestMatchers("/doctor/**").hasRole("DOCTOR")
                    .requestMatchers("/admin/**").hasRole("SUPER_ADMIN")
                    .requestMatchers("/patient/**").hasRole("PATIENT")
                    .requestMatchers("/administration/info").hasAnyRole("ADMINISTRATION","PATIENT")
                    .anyRequest()
                    .authenticated();

        });

        http.formLogin(manager -> {
            manager
//                    .loginPage("/login")
//                    .usernameParameter("phone")
//                    .passwordParameter("password");
//                    .defaultSuccessUrl("/login/test");
                    .successHandler(customAuthenticationSuccessHandler());
        });



        return http.build();
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                String redirectUrl;
                List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
                if (roles.contains(RoleName.ROLE_SUPER_ADMIN.toString())) {
                    redirectUrl = "/admin";
                } else if (roles.contains(RoleName.ROLE_ADMINISTRATION.toString())) {
                    redirectUrl = "/administration";
                } else if (roles.contains(RoleName.ROLE_DOCTOR.toString())) {
                    redirectUrl = "/doctor";
                } else if (roles.contains(RoleName.ROLE_PATIENT.toString())) {
                    redirectUrl = "/patient";
                } else {
                    redirectUrl = null;
                }
                if (redirectUrl != null) {
                    getRedirectStrategy().sendRedirect(request, response, redirectUrl);
                } else {
                    response.sendRedirect("/login?error");
                }
            }
        };
    }

}