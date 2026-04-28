package com.standofit.back.modules.exercises;

import jakarta.persistence.*;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExerciseMuscleGroup muscleGroup;

    public Exercise() {}

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ExerciseMuscleGroup getMuscleGroup() { return muscleGroup; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setMuscleGroup(ExerciseMuscleGroup muscleGroup) { this.muscleGroup = muscleGroup; }
}