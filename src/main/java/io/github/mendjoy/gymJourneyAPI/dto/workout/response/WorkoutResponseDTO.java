package io.github.mendjoy.gymJourneyAPI.dto.workout.response;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;

import java.time.LocalDate;
import java.util.List;

public class WorkoutResponseDTO extends WorkoutDTO {

    List<WorkoutSectionResponseDTO> workoutSections;

    public WorkoutResponseDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, LocalDate startDate, LocalDate endDate) {
        super(id, userId, name, description, maxSessions, startDate, endDate);
    }

    public WorkoutResponseDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, LocalDate startDate, LocalDate endDate, List<WorkoutSectionResponseDTO> workoutSections) {
        super(id, userId, name, description, maxSessions, startDate, endDate);
        this.workoutSections = workoutSections;
    }

    public List<WorkoutSectionResponseDTO> getWorkoutSections() {
        return workoutSections;
    }

    public void setWorkoutSections(List<WorkoutSectionResponseDTO> workoutSections) {
        this.workoutSections = workoutSections;
    }
}
