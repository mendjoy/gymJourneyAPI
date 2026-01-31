package io.github.mendjoy.gymJourneyAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "workout_exercise")
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_section_id", nullable = false)
    @JsonBackReference
    private WorkoutSection workoutSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Positive
    @NotNull
    @Column(nullable = false)
    private Integer sets;

    @Positive
    @NotNull
    @Column(nullable = false)
    private Integer repetitions;

    @Min(0)
    private Double weight;

    @Min(0)
    @Column(name = "rest_time")
    private Integer restTime;

    public WorkoutExercise() {
    }

    public WorkoutExercise(WorkoutSection workoutSection, Exercise exercise,
                           Integer sets, Integer repetitions, Double weight, Integer restTime) {
        this.workoutSection = workoutSection;
        this.exercise = exercise;
        this.sets = sets;
        this.repetitions = repetitions;
        this.weight = weight;
        this.restTime = restTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutSection getWorkoutSection() {
        return workoutSection;
    }

    public void setWorkoutSection(WorkoutSection workoutSection) {
        this.workoutSection = workoutSection;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getRestTime() {
        return restTime;
    }

    public void setRestTime(Integer restTime) {
        this.restTime = restTime;
    }

    public void update(WorkoutExerciseDto workoutExerciseDto) {
        if (workoutExerciseDto.sets() != null && workoutExerciseDto.sets() > 0) {
            this.setSets(workoutExerciseDto.sets());
        }
        if (workoutExerciseDto.repetitions() != null && workoutExerciseDto.repetitions() > 0) {
            this.setRepetitions(workoutExerciseDto.repetitions());
        }
        if (workoutExerciseDto.weight() != null && workoutExerciseDto.weight() >= 0) {
            this.setWeight(workoutExerciseDto.weight());
        }
        if (workoutExerciseDto.restTime() != null && workoutExerciseDto.restTime() >= 0) {
            this.setRestTime(workoutExerciseDto.restTime());
        }
    }
}