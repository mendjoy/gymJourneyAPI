package io.github.mendjoy.gymJourneyAPI.domain;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDto;
import jakarta.persistence.*;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
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

    public void updateExercise(ExerciseDto exerciseDto) {
        if(exerciseDto.name() != null && !exerciseDto.name().isBlank()){
            this.setName(exerciseDto.name());
        }
        if(exerciseDto.description() != null && !exerciseDto.description().isBlank()){
            this.setDescription(exerciseDto.description());
        }
        if(exerciseDto.muscleGroup() != null && !exerciseDto.muscleGroup().isBlank()){
            this.setMuscleGroup(exerciseDto.muscleGroup());
        }
    }
}
