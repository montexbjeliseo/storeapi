package com.mtxbjls.storeapi.security.repositories;

import com.mtxbjls.storeapi.security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);
    Role findByName(String name);
}
