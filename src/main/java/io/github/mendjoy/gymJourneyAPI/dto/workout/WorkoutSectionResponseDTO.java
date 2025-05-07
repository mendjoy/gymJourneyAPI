package io.github.mendjoy.gymJourneyAPI.dto.workout;

import java.util.List;

public class WorkoutSectionDetailsDTO {
    private Integer id;
    private String name;
    private String description;
    private List<WorkoutExerciseDetailsDTO> workoutExerciseDetailsDTOS;

    public WorkoutSectionDetailsDTO() {
    }

    public WorkoutSectionDetailsDTO(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public WorkoutSectionDetailsDTO(Integer id, String name, String description, List<WorkoutExerciseDetailsDTO> workoutExerciseDetailsDTOS) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.workoutExerciseDetailsDTOS = workoutExerciseDetailsDTOS;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WorkoutExerciseDetailsDTO> getWorkoutExerciseDetailsDTOS() {
        return workoutExerciseDetailsDTOS;
    }

    public void setWorkoutExerciseDetailsDTOS(List<WorkoutExerciseDetailsDTO> workoutExerciseDetailsDTOS) {
        this.workoutExerciseDetailsDTOS = workoutExerciseDetailsDTOS;
    }
}
