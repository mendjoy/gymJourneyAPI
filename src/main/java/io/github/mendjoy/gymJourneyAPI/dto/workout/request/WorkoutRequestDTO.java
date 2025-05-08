package io.github.mendjoy.gymJourneyAPI.dto.workout.request;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDTO;

import java.util.List;

public class WorkoutRequestDTO extends WorkoutDTO {

    private List<WorkoutSectionRequestDTO> workoutSectionRequestDTOS;

    public WorkoutRequestDTO(List<WorkoutSectionRequestDTO> workoutSectionRequestDTOS) {
        this.workoutSectionRequestDTOS = workoutSectionRequestDTOS;
    }

    public WorkoutRequestDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, List<WorkoutSectionRequestDTO> workoutSectionRequestDTOS) {
        super(id, userId, name, description, maxSessions);
        this.workoutSectionRequestDTOS = workoutSectionRequestDTOS;
    }

    public List<WorkoutSectionRequestDTO> getWorkoutSectionRequestDTOS() {
        return workoutSectionRequestDTOS;
    }

    public void setWorkoutSectionRequestDTOS(List<WorkoutSectionRequestDTO> workoutSectionRequestDTOS) {
        this.workoutSectionRequestDTOS = workoutSectionRequestDTOS;
    }
}
