package com.carsonbreslow.fitness_tracker.model;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @OneToMany(mappedBy = "workoutExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SetEntry> sets = new ArrayList<>();

    public WorkoutExercise() {
        // required by JPA
    }

    public WorkoutExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void addSet(int reps, double weight) {
        SetEntry set = new SetEntry(reps, weight);
        set.setWorkoutExercise(this);
        sets.add(set);
    }

    public double getHighestWeight() {
        double highest = 0;
        for (SetEntry set : sets) {
            if (set.getWeight() > highest) {
                highest = set.getWeight();
            }
        }
        return highest;
    }

    public Long getId() {
        return id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public List<SetEntry> getSets() {
        return sets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SetEntry entry : sets) {
            sb.append(entry.toString());
            sb.append(" -");
        }
        return sb.toString();
    }
}