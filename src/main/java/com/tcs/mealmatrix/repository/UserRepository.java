package com.tcs.mealmatrix.repository;

import com.tcs.mealmatrix.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String username);
    boolean existsByEmail(String email);
}
