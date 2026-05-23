package com.tcs.mealmatrix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AdminInitializer {
    private final String ADMIN_EMAIL;
    private final Map<String, UserDetails> inMemoryAdmins;

    public AdminInitializer(@Value("${admin.email}") String ADMIN_EMAIL, @Value("${admin.password}") String ADMIN_PASSWORD, PasswordEncoder passwordEncoder) {
        this.ADMIN_EMAIL = ADMIN_EMAIL;
        inMemoryAdmins = new ConcurrentHashMap<>();

        UserDetails admin = User.builder()
                .username(ADMIN_EMAIL)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .roles("ADMIN")
                .build();
        inMemoryAdmins.put(ADMIN_EMAIL, admin);
        System.out.println("IN Memory admin added and loaded successfully");
    }

    public Optional<UserDetails> findAdminByEmail(String email) {
        return Optional.ofNullable(inMemoryAdmins.get(email));
    }

    public boolean isAdminEmail(String email) {
        return ADMIN_EMAIL.equalsIgnoreCase(email);
    }
}
