package com.tcs.mealmatrix.security;

import com.tcs.mealmatrix.config.AdminInitializer;
import com.tcs.mealmatrix.model.AppUser;
import com.tcs.mealmatrix.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminInitializer adminInitializer;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return adminInitializer.findAdminByEmail(email)
                .orElseGet(() -> loadUserFromDatabase(email));
    }

    public UserDetails loadUserFromDatabase(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
}
