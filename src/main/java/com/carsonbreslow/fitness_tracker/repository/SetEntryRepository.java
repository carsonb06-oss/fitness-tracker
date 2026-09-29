package com.carsonbreslow.fitness_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carsonbreslow.fitness_tracker.model.SetEntry;

public interface SetEntryRepository extends JpaRepository<SetEntry, Long> {
}