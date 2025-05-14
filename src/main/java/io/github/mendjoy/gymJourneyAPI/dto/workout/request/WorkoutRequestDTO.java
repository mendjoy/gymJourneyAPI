package io.github.mendjoy.gymJourneyAPI.dto.workout.request;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;

import java.util.Date;
import java.util.List;

public class WorkoutRequestDTO extends WorkoutDTO {

    private List<WorkoutSectionRequestDTO> workoutSections;

    public WorkoutRequestDTO() {
    }

    public WorkoutRequestDTO(List<WorkoutSectionRequestDTO> workoutSections) {
        this.workoutSections = workoutSections;
    }

    public WorkoutRequestDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, Date startDate, Date endDate, List<WorkoutSectionRequestDTO> workoutSections) {
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
