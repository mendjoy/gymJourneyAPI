package io.github.mendjoy.gymJourneyAPI.dto.workout;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDto;

public class WorkoutExerciseDetailsDto {
    private Long id;
    private Integer sets;
    private Integer repetitions;
    private Double weight;
    private Integer restTime;
    private ExerciseDto exercise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ExerciseDto getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDto exercise) {
        this.exercise = exercise;
    }
}
