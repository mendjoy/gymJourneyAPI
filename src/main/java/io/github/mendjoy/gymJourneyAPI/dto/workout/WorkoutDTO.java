package io.github.mendjoy.gymJourneyAPI.dto.workout;


import java.util.List;

public class WorkoutDTO extends WorkoutBaseDTO {

    private List<WorkoutExerciseDTO> exercises;

    public WorkoutDTO() {
    }

    public WorkoutDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, List<WorkoutExerciseDTO> exercises) {
        super();
        this.exercises = exercises;
    }

    public List<WorkoutExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExercises(List<WorkoutExerciseDTO> exercises) {
        this.exercises = exercises;
    }
}
