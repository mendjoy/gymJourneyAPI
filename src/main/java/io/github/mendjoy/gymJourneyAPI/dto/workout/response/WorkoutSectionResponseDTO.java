package io.github.mendjoy.gymJourneyAPI.dto.workout.response;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDTO;

import java.util.List;

public class WorkoutSectionResponseDTO extends WorkoutSectionDTO {

    private List<WorkoutExerciseResponseDTO> workoutExerciseResponseDTOS;

    public WorkoutSectionResponseDTO() {
    }

    public WorkoutSectionResponseDTO(List<WorkoutExerciseResponseDTO> workoutExerciseResponseDTOS) {
        this.workoutExerciseResponseDTOS = workoutExerciseResponseDTOS;
    }

    public WorkoutSectionResponseDTO(Integer id, String name, String description, List<WorkoutExerciseResponseDTO> workoutExerciseResponseDTOS) {
        super(id, name, description);
        this.workoutExerciseResponseDTOS = workoutExerciseResponseDTOS;
    }
}
