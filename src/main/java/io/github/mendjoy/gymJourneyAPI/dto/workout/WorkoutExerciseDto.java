package io.github.mendjoy.gymJourneyAPI.dto.workout;

import jakarta.validation.constraints.NotNull;

public class WorkoutExerciseDto {
    private Long id;

    @NotNull(message = "O id da secao deve ser informado!")
    private Long workoutSectionId;

    @NotNull(message = "O id do exercicio deve ser informado!")
    private Long exerciseId;

    @NotNull(message = "O numero de series deve ser informado!")
    private Integer sets;

    @NotNull(message = "O numero de repetições deve ser informado!")
    private Integer repetitions;

    private Double weight;

    @NotNull(message = "O tempo de descanso deve ser informado!")
    private Integer restTime;

    public WorkoutExerciseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkoutSectionId() {
        return workoutSectionId;
    }

    public void setWorkoutSectionId(Long workoutSectionId) {
        this.workoutSectionId = workoutSectionId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
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
}
