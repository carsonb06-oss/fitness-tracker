package com.carsonbreslow.fitness_tracker.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.carsonbreslow.fitness_tracker.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}