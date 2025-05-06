package io.github.mendjoy.gymJourneyAPI.dto.workout;


import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDTO;

import java.util.List;

public class WorkoutSectionDTO {
    private Integer id;
    private String name;
    private String description;
    private List<WorkoutExerciseDTO> workoutExerciseDTOS;

    public WorkoutSectionDTO() {
    }

    public WorkoutSectionDTO(Integer id, String name, String description, List<WorkoutExerciseDTO> workoutExerciseDTOS) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.workoutExerciseDTOS = workoutExerciseDTOS;
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

    public List<WorkoutExerciseDTO> getWorkoutExerciseDTOS() {
        return workoutExerciseDTOS;
    }

    public void setWorkoutExerciseDTOS(List<WorkoutExerciseDTO> workoutExerciseDTOS) {
        this.workoutExerciseDTOS = workoutExerciseDTOS;
    }
}
