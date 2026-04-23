package com.standofit.back.modules.exercises.repository;

import com.standofit.back.modules.exercises.Exercise;
import com.standofit.back.modules.exercises.ExerciseMuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String> {
    List<Exercise> findByMuscleGroup(ExerciseMuscleGroup muscleGroup);
    List<Exercise> findByNameContainingIgnoreCase(String name);
}