package com.app.ecommerce.security.repository;

import com.app.ecommerce.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    User findByResetPasswordToken(String token);

    User findByRegisterToken(String token);
}
