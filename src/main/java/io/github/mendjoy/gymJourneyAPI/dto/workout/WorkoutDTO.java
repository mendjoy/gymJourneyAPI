package io.github.mendjoy.gymJourneyAPI.dto.workout;


import java.util.List;

public class WorkoutDTO extends WorkoutBaseDTO {

    private List<WorkoutSectionDTO> workoutSectionDTOS;

    public WorkoutDTO() {
    }

    public WorkoutDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, List<WorkoutSectionDTO> workoutSectionDTOS) {
        super(id, userId, name, description, maxSessions);
        this.workoutSectionDTOS = workoutSectionDTOS;
    }

    public WorkoutDTO(List<WorkoutSectionDTO> workoutSectionDTOS) {
        this.workoutSectionDTOS = workoutSectionDTOS;
    }

    public List<WorkoutSectionDTO> getWorkoutSections() {
        return workoutSectionDTOS;
    }

    public void setWorkoutSections(List<WorkoutSectionDTO> workoutSectionDTOS) {
        this.workoutSectionDTOS = workoutSectionDTOS;
    }
}
