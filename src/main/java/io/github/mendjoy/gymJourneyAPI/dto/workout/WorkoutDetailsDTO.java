package io.github.mendjoy.gymJourneyAPI.dto.workout;

import java.util.List;

public class WorkoutDetailsDTO extends WorkoutBaseDTO {

    private List<WorkoutExerciseDetailsDTO> exercises;

    public WorkoutDetailsDTO() {
    }

    public WorkoutDetailsDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, List<WorkoutExerciseDetailsDTO> exercises) {
        super();
        this.exercises = exercises;
    }

    public List<WorkoutExerciseDetailsDTO> getExercises() {
        return exercises;
    }

    public void setExercises(List<WorkoutExerciseDetailsDTO> exercises) {
        this.exercises = exercises;
    }
}
