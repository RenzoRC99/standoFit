package com.standofit.back.modules.exercises.controller;

import com.standofit.back.modules.exercises.Exercise;
import com.standofit.back.modules.exercises.ExerciseMuscleGroup;
import com.standofit.back.modules.exercises.dto.ExerciseRequest;
import com.standofit.back.modules.exercises.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService service;

    public ExerciseController(ExerciseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Exercise> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Exercise findById(@PathVariable String id) {
        Exercise exercise = service.findById(id);
        if (exercise == null) {
            throw new ExerciseNotFoundException(id);
        }
        return exercise;
    }

    @GetMapping("/muscle-group/{muscleGroup}")
    public List<Exercise> findByMuscleGroup(@PathVariable ExerciseMuscleGroup muscleGroup) {
        return service.findByMuscleGroup(muscleGroup);
    }

    @GetMapping("/search")
    public List<Exercise> search(@RequestParam String name) {
        return service.search(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Exercise create(@RequestBody ExerciseRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public Exercise update(@PathVariable String id, @RequestBody ExerciseRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @ExceptionHandler(ExerciseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(ExerciseNotFoundException e) {}

    public static class ExerciseNotFoundException extends RuntimeException {
        public ExerciseNotFoundException(String id) {
            super("Exercise not found: " + id);
        }
    }
}