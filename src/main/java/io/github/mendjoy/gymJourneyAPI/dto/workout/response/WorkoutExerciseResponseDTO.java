package io.github.mendjoy.gymJourneyAPI.dto.workout.response;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDTO;

public class WorkoutExerciseResponseDTO extends WorkoutExerciseDTO {
    private ExerciseDTO exercise;

    public WorkoutExerciseResponseDTO() {
    }

    public WorkoutExerciseResponseDTO(Integer id, Integer sets, Integer repetitions, Double weight, Integer restTime, ExerciseDTO exercise) {
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
