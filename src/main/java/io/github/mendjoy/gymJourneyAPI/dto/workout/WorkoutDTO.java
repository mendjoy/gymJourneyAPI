package io.github.mendjoy.gymJourneyAPI.dto.workout;


import java.util.List;

public class WorkoutDTO {
    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private Integer maxSessions;
    private List<WorkoutExerciseDTO> exercises;

    public WorkoutDTO() {
    }

    public WorkoutDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, List<WorkoutExerciseDTO> exercises) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.maxSessions = maxSessions;
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

    public Integer getMaxSessions() {
        return maxSessions;
    }

    public void setMaxSessions(Integer maxSessions) {
        this.maxSessions = maxSessions;
    }

    public void setExercises(List<WorkoutExerciseDTO> exercises) {
        this.exercises = exercises;
    }

    public List<WorkoutExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExercise(List<WorkoutExerciseDTO> exercises) {
        this.exercises = exercises;
    }
}
