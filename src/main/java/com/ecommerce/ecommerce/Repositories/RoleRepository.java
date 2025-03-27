package com.ecommerce.ecommerce.Repositories;

import com.ecommerce.ecommerce.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(String role);
}
