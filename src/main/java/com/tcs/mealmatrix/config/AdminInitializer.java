package com.tcs.mealmatrix.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AdminInitializer {

    private final String adminEmail;
    private final String adminPassword;

    public AdminInitializer(
            @Value("${admin.email}") String adminEmail,
            @Value("${admin.password}") String adminPassword
    ) {

        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;

        System.out.println("ADMIN EMAIL = " + adminEmail);
        System.out.println("ADMIN PASSWORD = " + adminPassword);
    }

    public boolean isAdminEmail(String email) {
        return adminEmail.equalsIgnoreCase(email);
    }
}