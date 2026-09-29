package com.carsonbreslow.fitness_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carsonbreslow.fitness_tracker.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}