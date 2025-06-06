package io.github.mendjoy.gymJourneyAPI.dto.workout.request;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public class WorkoutRequestDTO extends WorkoutDTO {

    @Valid
    @NotEmpty(message = "O treino deve conter ao menos uma seção")
    private List<WorkoutSectionRequestDTO> workoutSections;

    public WorkoutRequestDTO() {
    }

    public WorkoutRequestDTO(List<WorkoutSectionRequestDTO> workoutSections) {
        this.workoutSections = workoutSections;
    }

    public WorkoutRequestDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, LocalDate startDate, LocalDate endDate, List<WorkoutSectionRequestDTO> workoutSections) {
        super(id, userId, name, description, maxSessions, startDate, endDate);
        this.workoutSections = workoutSections;
    }

    public List<WorkoutSectionRequestDTO> getWorkoutSections() {
        return workoutSections;
    }

    public void setWorkoutSections(List<WorkoutSectionRequestDTO> workoutSections) {
        this.workoutSections = workoutSections;
    }
}
