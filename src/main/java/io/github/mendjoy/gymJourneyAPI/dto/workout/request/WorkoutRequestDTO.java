package io.github.mendjoy.gymJourneyAPI.dto.workout.request;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;

import java.util.Date;
import java.util.List;

public class WorkoutRequestDTO extends WorkoutDTO {

    private List<WorkoutSectionRequestDTO> workoutSection;

    public WorkoutRequestDTO() {
    }

    public WorkoutRequestDTO(List<WorkoutSectionRequestDTO> workoutSection) {
        this.workoutSection = workoutSection;
    }

    public WorkoutRequestDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, Date startDate, Date endDate, List<WorkoutSectionRequestDTO> workoutSection) {
        super(id, userId, name, description, maxSessions, startDate, endDate);
        this.workoutSection = workoutSection;
    }

    public List<WorkoutSectionRequestDTO> getWorkoutSection() {
        return workoutSection;
    }

    public void setWorkoutSection(List<WorkoutSectionRequestDTO> workoutSection) {
        this.workoutSection = workoutSection;
    }
}
