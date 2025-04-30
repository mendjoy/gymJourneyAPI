package io.github.mendjoy.gymJourneyAPI.dto.exercise;

public class ExerciseDTO {

    private Integer id;
    private String name;
    private String description;
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
