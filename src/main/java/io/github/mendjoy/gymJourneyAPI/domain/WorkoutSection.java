package io.github.mendjoy.gymJourneyAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutSectionDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_section")
public class WorkoutSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    @JsonBackReference
    private Workout workout;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "workoutSection", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WorkoutExercise> workoutExercises = new ArrayList<>();

    public WorkoutSection() {
    }

    public WorkoutSection(Workout workout, String name, String description,
                          List<WorkoutExercise> workoutExercises) {
        this.workout = workout;
        this.name = name;
        this.description = description;
        this.workoutExercises = workoutExercises != null ? workoutExercises : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void addWorkoutExercise(WorkoutExercise exercise) {
        workoutExercises.add(exercise);
        exercise.setWorkoutSection(this);
    }

    public void update(WorkoutSectionDto workoutSectionDto) {
        if (workoutSectionDto.name() != null && !workoutSectionDto.name().isBlank()) {
            this.setName(workoutSectionDto.name());
        }
        if (workoutSectionDto.description() != null && !workoutSectionDto.description().isBlank()) {
            this.setDescription(workoutSectionDto.description());
        }
    }
}