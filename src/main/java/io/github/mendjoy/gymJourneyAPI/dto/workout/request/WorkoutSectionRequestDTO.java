package io.github.mendjoy.gymJourneyAPI.dto.workout.request;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDTO;

import java.util.List;

public class WorkoutSectionRequestDTO extends WorkoutSectionDTO {

    private List<WorkoutExerciseRequestDTO> workoutExercise;

    public WorkoutSectionRequestDTO() {
    }

    public WorkoutSectionRequestDTO(Integer id, String name, String description) {
        super(id, name, description);
    }

    public WorkoutSectionRequestDTO(Integer id, String name, String description, List<WorkoutExerciseRequestDTO> workoutExercise) {
        super(id, name, description);
        this.workoutExercise = workoutExercise;
    }

    public List<WorkoutExerciseRequestDTO> getWorkoutExercise() {
        return workoutExercise;
    }

    public void setWorkoutExercise(List<WorkoutExerciseRequestDTO> workoutExercise) {
        this.workoutExercise = workoutExercise;
    }
}
