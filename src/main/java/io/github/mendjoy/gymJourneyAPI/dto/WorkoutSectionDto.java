package io.github.mendjoy.gymJourneyAPI.dto;

import java.util.List;

public class WorkoutSectionDto {
    private Integer id;
    private String name;
    private String description;
    private List<WorkoutExerciseDto> workoutExercises;

    public WorkoutSectionDto() {
    }

    public WorkoutSectionDto(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<WorkoutExerciseDto> getWorkoutExercises() {
        return workoutExercises;
    }

    public void setWorkoutExercises(List<WorkoutExerciseDto> workoutExercises) {
        this.workoutExercises = workoutExercises;
    }
}
