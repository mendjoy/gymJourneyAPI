package io.github.mendjoy.gymJourneyAPI.dto.workout.response;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDTO;

import java.util.List;

public class WorkoutSectionResponseDTO extends WorkoutSectionDTO {

    private List<WorkoutExerciseResponseDTO> workoutExercise;

    public WorkoutSectionResponseDTO() {
    }

    public WorkoutSectionResponseDTO(List<WorkoutExerciseResponseDTO> workoutExercise) {
        this.workoutExercise = workoutExercise;
    }

    public WorkoutSectionResponseDTO(Integer id, String name, String description, List<WorkoutExerciseResponseDTO> workoutExercise) {
        super(id, name, description);
        this.workoutExercise = workoutExercise;
    }
}
