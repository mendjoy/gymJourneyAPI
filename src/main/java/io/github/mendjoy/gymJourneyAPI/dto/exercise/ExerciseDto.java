package io.github.mendjoy.gymJourneyAPI.dto.exercise;

import jakarta.validation.constraints.NotBlank;

public class ExerciseDto{

    private Integer id;

    @NotBlank(message = "O nome do exercicio deve ser informado!")
    private String name;

    @NotBlank(message = "A descricao do exercicio deve ser informada!")
    private String description;

    @NotBlank(message = "O grupo muscular deve ser informado!")
    private String muscleGroup;

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

