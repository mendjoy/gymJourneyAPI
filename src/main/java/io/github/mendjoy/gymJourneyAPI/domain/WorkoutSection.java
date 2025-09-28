package io.github.mendjoy.gymJourneyAPI.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "workout_section")
public class WorkoutSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;
    private String name;
    private String description;

    @OneToMany(mappedBy = "workoutSection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutExercise> workoutExercises;

    public WorkoutSection() {
    }

    public WorkoutSection(Workout workout, String name, String description, List<WorkoutExercise> workoutExercises) {
        this.workout = workout;
        this.name = name;
        this.description = description;
        this.workoutExercises = workoutExercises;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
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

    public List<WorkoutExercise> getWorkoutExercises() {
        return workoutExercises;
    }

    public void setWorkoutExercises(List<WorkoutExercise> workoutExercises) {
        this.workoutExercises = workoutExercises;
    }
}
