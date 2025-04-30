package io.github.mendjoy.gymJourneyAPI.dto.workout;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDTO;

public class WorkoutExerciseDetailsDTO extends WorkoutExerciseBaseDTO {
    private ExerciseDTO exercise;

    public WorkoutExerciseDetailsDTO() {
    }

    public WorkoutExerciseDetailsDTO(Integer id, ExerciseDTO exercise, Integer sets, Integer repetitions, Double weight, Integer restTime) {
        super(id, sets, repetitions, weight, restTime);
        this.exercise = exercise;

    }

    public ExerciseDTO getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDTO exercise) {
        this.exercise = exercise;
    }
}
