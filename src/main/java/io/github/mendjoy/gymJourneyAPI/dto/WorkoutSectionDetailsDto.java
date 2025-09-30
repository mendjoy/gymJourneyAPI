package io.github.mendjoy.gymJourneyAPI.dto;

import java.util.List;

public class WorkoutSectionDetailsDto {
    private Integer id;
    private Integer workoutId;
    private String name;
    private String description;
    private List<WorkoutExerciseDetailsDto> workoutExercises;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WorkoutExerciseDetailsDto> getWorkoutExercises() {
        return workoutExercises;
    }

    public void setWorkoutExercises(List<WorkoutExerciseDetailsDto> workoutExercises) {
        this.workoutExercises = workoutExercises;
    }
}
