package io.github.mendjoy.gymJourneyAPI.dto.workout;


import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDTO;

import java.util.List;

public class WorkoutSectionDTO {
    private Integer id;
    private String name;
    private String description;
    private List<ExerciseDTO> exerciseDTOS;

    public WorkoutSectionDTO() {
    }

    public WorkoutSectionDTO(Integer id, String name, String description, List<ExerciseDTO> exerciseDTOS) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exerciseDTOS = exerciseDTOS;
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

    public List<ExerciseDTO> getExerciseDTOS() {
        return exerciseDTOS;
    }

    public void setExerciseDTOS(List<ExerciseDTO> exerciseDTOS) {
        this.exerciseDTOS = exerciseDTOS;
    }
}
