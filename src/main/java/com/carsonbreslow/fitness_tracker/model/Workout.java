package com.carsonbreslow.fitness_tracker.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workout_id")
    private List<WorkoutExercise> exercises = new ArrayList<>();

    public Workout() {
        // required by JPA
    }

    public Workout(LocalDate date) {
        this.date = date;
    }

    public void addExercise(WorkoutExercise exercise) {
        exercises.add(exercise);
    }

    public double getTotalVolume() {
        double total = 0;
        for (WorkoutExercise we : exercises) {
            for (var set : we.getSets()) {
                total += set.getVolume();
            }
        }
        return total;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<WorkoutExercise> getExercises() {
        return exercises;
    }
}