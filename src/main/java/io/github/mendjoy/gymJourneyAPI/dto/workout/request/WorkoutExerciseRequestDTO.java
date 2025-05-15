package io.github.mendjoy.gymJourneyAPI.dto.workout.request;

import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutExerciseDTO;
import jakarta.validation.constraints.NotNull;

public class WorkoutExerciseRequestDTO extends WorkoutExerciseDTO {

    @NotNull(message = "O id do Exercicio deve ser informado")
    private Integer exerciseId;

    public WorkoutExerciseRequestDTO() {
    }

    public WorkoutExerciseRequestDTO(Integer id, Integer sets, Integer repetitions, Double weight, Integer restTime) {
        super(id, sets, repetitions, weight, restTime);
    }

    public WorkoutExerciseRequestDTO(Integer id, Integer exerciseId, Integer sets, Integer repetitions, Double weight, Integer restTime) {
        super(id, sets, repetitions, weight, restTime);
        this.exerciseId = exerciseId;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }
}
