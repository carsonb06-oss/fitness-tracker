package com.carsonbreslow.fitness_tracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.carsonbreslow.fitness_tracker.model.WorkoutExercise;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
    List<WorkoutExercise> findByExerciseNameIgnoreCase(String name);
}