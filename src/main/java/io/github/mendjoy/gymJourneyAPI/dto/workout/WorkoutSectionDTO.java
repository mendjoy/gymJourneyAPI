package io.github.mendjoy.gymJourneyAPI.dto.workout;


import jakarta.validation.constraints.NotBlank;

public class WorkoutSectionDTO {
    private Integer id;
    @NotBlank(message = "O Nome da secao deve ser informado")
    private String name;
    private String description;

    public WorkoutSectionDTO() {
    }

    public WorkoutSectionDTO(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
