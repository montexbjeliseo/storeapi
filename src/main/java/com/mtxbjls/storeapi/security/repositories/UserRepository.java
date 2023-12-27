package com.mtxbjls.storeapi.security.repositories;

import com.mtxbjls.storeapi.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String username);
}
