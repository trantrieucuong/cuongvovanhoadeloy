package org.example.cuongvanhoa.repository;


import org.example.cuongvanhoa.entity.User;
import org.example.cuongvanhoa.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByRole(UserRole userRole);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}