package io.github.mendjoy.gymJourneyAPI.dto.workout.response;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;

import java.util.List;

public class WorkoutResponseDTO extends WorkoutDTO {

    List<WorkoutSectionResponseDTO> workoutSection;

    public WorkoutResponseDTO(Integer id, Integer userId, String name, String description, Integer maxSessions) {
        super(id, userId, name, description, maxSessions);
    }

    public WorkoutResponseDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, List<WorkoutSectionResponseDTO> workoutSection) {
        super(id, userId, name, description, maxSessions);
        this.workoutSection = workoutSection;
    }

    public List<WorkoutSectionResponseDTO> getWorkoutSection() {
        return workoutSection;
    }

    public void setWorkoutSection(List<WorkoutSectionResponseDTO> workoutSection) {
        this.workoutSection = workoutSection;
    }
}
