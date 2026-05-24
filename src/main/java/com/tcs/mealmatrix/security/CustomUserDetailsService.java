package com.tcs.mealmatrix.security;

import com.tcs.mealmatrix.config.AdminInitializer;
import com.tcs.mealmatrix.model.AppUser;
import com.tcs.mealmatrix.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminInitializer adminInitializer;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        System.out.println("Trying to load: " + email);

        // ADMIN
        if(adminInitializer.isAdminEmail(email)) {

            System.out.println("ADMIN LOGIN DETECTED");

            return User.builder()
                    .username(adminInitializer.getAdminEmail())
                    .password(
                            passwordEncoder.encode(
                                    adminInitializer.getAdminPassword()
                            )
                    )
                    .roles("ADMIN")
                    .build();
        }

        // DATABASE USER
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        System.out.println("DATABASE USER FOUND");

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name().replace("ROLE_", ""))
                .build();
    }
}