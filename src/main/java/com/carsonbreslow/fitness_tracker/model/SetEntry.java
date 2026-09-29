package com.carsonbreslow.fitness_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class SetEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int reps;
    private double weight;

    @ManyToOne
    @JoinColumn(name = "workout_exercise_id")
    @JsonBackReference
    private WorkoutExercise workoutExercise;

    public SetEntry() {
        // required by JPA
    }

    public SetEntry(int reps, double weight) {
        this.reps = reps;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public double getVolume() {
        return reps * weight;
    }

    public WorkoutExercise getWorkoutExercise() {
        return workoutExercise;
    }

    public void setWorkoutExercise(WorkoutExercise workoutExercise) {
        this.workoutExercise = workoutExercise;
    }

    @Override
    public String toString() {
        return reps + " reps @ " + weight + " lbs";
    }
}