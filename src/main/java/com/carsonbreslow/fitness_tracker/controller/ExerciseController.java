package com.carsonbreslow.fitness_tracker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.carsonbreslow.fitness_tracker.model.Exercise;
import com.carsonbreslow.fitness_tracker.model.WorkoutExercise;
import com.carsonbreslow.fitness_tracker.repository.ExerciseRepository;
import com.carsonbreslow.fitness_tracker.repository.WorkoutExerciseRepository;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

    @GetMapping
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/personal-record/{name}")
    public double getPersonalRecord(@PathVariable String name) {
        List<WorkoutExercise> matches = workoutExerciseRepository.findByExerciseNameIgnoreCase(name);

        double max = 0;
        for (WorkoutExercise we : matches) {
            if (we.getHighestWeight() > max) {
                max = we.getHighestWeight();
            }
        }
        return max;
    }

    @PostMapping
    public Exercise createExercise(@RequestBody Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        if (!exerciseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        exerciseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}