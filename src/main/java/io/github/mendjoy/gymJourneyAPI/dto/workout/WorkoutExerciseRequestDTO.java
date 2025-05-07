package io.github.mendjoy.gymJourneyAPI.dto.workout;

public class WorkoutExerciseDTO extends WorkoutExerciseRequestDTO {
    private Integer exerciseId;

    public WorkoutExerciseDTO() {
    }

    public WorkoutExerciseDTO(Integer id, Integer exerciseId, Integer sets, Integer repetitions, Double weight, Integer restTime) {
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
