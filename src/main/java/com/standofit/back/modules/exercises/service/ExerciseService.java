package com.standofit.back.modules.exercises.service;

import com.standofit.back.modules.exercises.Exercise;
import com.standofit.back.modules.exercises.ExerciseMuscleGroup;
import com.standofit.back.modules.exercises.dto.ExerciseRequest;
import com.standofit.back.modules.exercises.repository.ExerciseRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {

  private final ExerciseRepository repository;

  public ExerciseService(ExerciseRepository repository) {
    this.repository = repository;
  }

  public List<Exercise> findAll() {
    return repository.findAll();
  }

  public Exercise findById(String id) {
    return repository.findById(id).orElse(null);
  }

  public List<Exercise> findByMuscleGroup(ExerciseMuscleGroup muscleGroup) {
    return repository.findByMuscleGroup(muscleGroup);
  }

  public List<Exercise> search(String name) {
    return repository.findByNameContainingIgnoreCase(name);
  }

  public Exercise create(ExerciseRequest request) {
    Exercise exercise = new Exercise();
    exercise.setName(request.name());
    exercise.setDescription(request.description());
    exercise.setMuscleGroup(ExerciseMuscleGroup.valueOf(request.muscleGroup()));
    return repository.save(exercise);
  }

  public Exercise update(String id, ExerciseRequest request) {
    Exercise exercise = findById(id);
    exercise.setName(request.name());
    exercise.setDescription(request.description());
    exercise.setMuscleGroup(ExerciseMuscleGroup.valueOf(request.muscleGroup()));
    return repository.save(exercise);
  }

  public void delete(String id) {
    repository.deleteById(id);
  }
}
