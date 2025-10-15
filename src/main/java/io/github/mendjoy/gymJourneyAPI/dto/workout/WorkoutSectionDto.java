package io.github.mendjoy.gymJourneyAPI.dto.workout;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WorkoutSectionDto {

    private Integer id;

    @NotNull(message = "O id do treino deve ser informado!")
    private Integer workoutId;

    @NotBlank(message = "O nome da seção deve ser informado!")
    private String name;

    @NotBlank(message = "A descrição deve ser informada!")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
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

}
