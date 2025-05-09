package io.github.mendjoy.gymJourneyAPI.dto.workout.response;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;

import java.util.List;

public class WorkoutResponseDTO extends WorkoutDTO {

    List<WorkoutSectionResponseDTO> workoutSections;

    public WorkoutResponseDTO(Integer id, Integer userId, String name, String description, Integer maxSessions) {
        super(id, userId, name, description, maxSessions);
    }

    public WorkoutResponseDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, List<WorkoutSectionResponseDTO> workoutSections) {
        super(id, userId, name, description, maxSessions);
        this.workoutSections = workoutSections;
    }

    public List<WorkoutSectionResponseDTO> getWorkoutSections() {
        return workoutSections;
    }

    public void setWorkoutSections(List<WorkoutSectionResponseDTO> workoutSections) {
        this.workoutSections = workoutSections;
    }
}
