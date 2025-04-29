package io.github.mendjoy.gymJourneyAPI.dto.workout;

import io.github.mendjoy.gymJourneyAPI.dto.exercise.ExerciseDTO;

public class WorkoutExerciseDetailsDTO extends WorkoutExerciseBaseDTO {
    private ExerciseDTO exerciseDTO;

    public WorkoutExerciseDetailsDTO() {
    }

    public WorkoutExerciseDetailsDTO(Integer id, ExerciseDTO exerciseDTO, Integer sets, Integer repetitions, Double weight, Integer restTime) {
        super(id, sets, repetitions, weight, restTime);
        this.exerciseDTO = exerciseDTO;

    }

    public ExerciseDTO getExerciseDTO() {
        return exerciseDTO;
    }

    public void setExerciseDTO(ExerciseDTO exerciseDTO) {
        this.exerciseDTO = exerciseDTO;
    }
}
