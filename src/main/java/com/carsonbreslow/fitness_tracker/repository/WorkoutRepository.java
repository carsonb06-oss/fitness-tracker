package com.carsonbreslow.fitness_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carsonbreslow.fitness_tracker.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}