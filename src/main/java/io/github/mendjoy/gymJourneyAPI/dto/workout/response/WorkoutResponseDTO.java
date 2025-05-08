package io.github.mendjoy.gymJourneyAPI.dto.workout.response;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;

import java.util.List;

public class WorkoutResponseDTO extends WorkoutDTO {

    List<WorkoutSectionResponseDTO> workoutSectionResponseDTOS;

    public WorkoutResponseDTO(Integer id, Integer userId, String name, String description, Integer maxSessions) {
        super(id, userId, name, description, maxSessions);
    }

    public WorkoutResponseDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, List<WorkoutSectionResponseDTO> workoutSectionResponseDTOS) {
        super(id, userId, name, description, maxSessions);
        this.workoutSectionResponseDTOS = workoutSectionResponseDTOS;
    }

    public List<WorkoutSectionResponseDTO> getWorkoutSectionResponseDTOS() {
        return workoutSectionResponseDTOS;
    }

    public void setWorkoutSectionResponseDTOS(List<WorkoutSectionResponseDTO> workoutSectionResponseDTOS) {
        this.workoutSectionResponseDTOS = workoutSectionResponseDTOS;
    }
}
