package io.github.mendjoy.gymJourneyAPI.dto.exercise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ExerciseDTO {

    private Integer id;

    @NotBlank(message = "O nome do exercício é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    private String name;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(min = 10, max = 255, message = "A descrição deve ter entre 10 e 255 caracteres.")
    private String description;

    @NotBlank(message = "O grupo muscular é obrigatório.")
    @Size(min = 2, max = 50, message = "O grupo muscular deve ter entre 2 e 50 caracteres.")
    private String muscleGroup;

    public ExerciseDTO() {
    }

    public ExerciseDTO(Integer id, String name, String description, String muscleGroup) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscleGroup = muscleGroup;
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

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
}
