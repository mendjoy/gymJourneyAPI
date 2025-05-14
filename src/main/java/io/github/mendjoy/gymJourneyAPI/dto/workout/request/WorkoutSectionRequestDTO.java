package io.github.mendjoy.gymJourneyAPI.dto.workout.request;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDTO;

import java.util.List;

public class WorkoutSectionRequestDTO extends WorkoutSectionDTO {

    private List<WorkoutExerciseRequestDTO> workoutExercises;

    public WorkoutSectionRequestDTO() {
    }

    public WorkoutSectionRequestDTO(Integer id, String name, String description) {
        super(id, name, description);
    }

    public WorkoutSectionRequestDTO(Integer id, String name, String description, List<WorkoutExerciseRequestDTO> workoutExercises) {
        super(id, name, description);
        this.workoutExercises = workoutExercises;
    }

    public List<WorkoutExerciseRequestDTO> getWorkoutExercises() {
        return workoutExercises;
    }

    public void setWorkoutExercises(List<WorkoutExerciseRequestDTO> workoutExercises) {
        this.workoutExercises = workoutExercises;
    }
}
