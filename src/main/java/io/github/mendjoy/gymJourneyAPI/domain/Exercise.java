package io.github.mendjoy.gymJourneyAPI.domain;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDto;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "exercise_muscle_group",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    private Set<MuscleGroup> muscleGroups = new HashSet<>();

    public Exercise() {
    }

    public Exercise(String name, String description, Set<MuscleGroup> muscleGroups) {
        this.name = name;
        this.description = description;
        this.muscleGroups = muscleGroups;
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

    public Set<MuscleGroup> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(Set<MuscleGroup> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public void updateExercise(ExerciseDto exerciseDto) {
        if(exerciseDto.name() != null && !exerciseDto.name().isBlank()){
            this.setName(exerciseDto.name());
        }
        if(exerciseDto.description() != null && !exerciseDto.description().isBlank()){
            this.setDescription(exerciseDto.description());
        }
    }
}
