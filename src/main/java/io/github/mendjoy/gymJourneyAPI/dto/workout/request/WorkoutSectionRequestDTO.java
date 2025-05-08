package io.github.mendjoy.gymJourneyAPI.dto.workout.request;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDTO;

import java.util.List;

public class WorkoutSectionRequestDTO extends WorkoutSectionDTO {

    private List<WorkoutExerciseRequestDTO> workoutExerciseRequestDTOS;

    public WorkoutSectionRequestDTO(Integer id, String name, String description) {
        super(id, name, description);
    }

    public WorkoutSectionRequestDTO(Integer id, String name, String description, List<WorkoutExerciseRequestDTO> workoutExerciseRequestDTOS) {
        super(id, name, description);
        this.workoutExerciseRequestDTOS = workoutExerciseRequestDTOS;
    }

    public List<WorkoutExerciseRequestDTO> getWorkoutExerciseRequestDTOS() {
        return workoutExerciseRequestDTOS;
    }

    public void setWorkoutExerciseRequestDTOS(List<WorkoutExerciseRequestDTO> workoutExerciseRequestDTOS) {
        this.workoutExerciseRequestDTOS = workoutExerciseRequestDTOS;
    }
}
