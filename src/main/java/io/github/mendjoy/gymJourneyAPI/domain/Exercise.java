package io.github.mendjoy.gymJourneyAPI.domain;

import io.github.mendjoy.gymJourneyAPI.dto.ExerciseDto;
import jakarta.persistence.*;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Column(name = "muscle_group")
    private String muscleGroup;

    public Exercise() {
    }

    public Exercise(String name, String description, String muscle_group) {
        this.name = name;
        this.description = description;
        this.muscleGroup = muscle_group;
    }

    public Integer getId() {
        return id;
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

    public void update(ExerciseDto exerciseDto) {
        if(exerciseDto.getName() != null){
            this.name = exerciseDto.getName();
        }
        if(exerciseDto.getDescription() != null){
            this.description = exerciseDto.getDescription();
        }
        if(exerciseDto.getMuscleGroup() != null){
            this.muscleGroup = exerciseDto.getMuscleGroup();
        }
    }
}
