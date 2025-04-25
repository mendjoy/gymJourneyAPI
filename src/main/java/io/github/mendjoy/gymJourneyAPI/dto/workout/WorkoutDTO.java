package io.github.mendjoy.gymJourneyAPI.dto.workout;


import java.util.List;

public class WorkoutDTO {
    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private List<WorkoutExerciseDTO> exercises;

    public WorkoutDTO() {
    }

    public WorkoutDTO(Integer id, Integer userId, String name, String description, List<WorkoutExerciseDTO> exercises) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.exercises = exercises;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public List<WorkoutExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExerciseDTOS(List<WorkoutExerciseDTO> exercises) {
        this.exercises = exercises;
    }
}
