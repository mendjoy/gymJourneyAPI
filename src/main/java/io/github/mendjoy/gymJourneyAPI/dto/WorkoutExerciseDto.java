package io.github.mendjoy.gymJourneyAPI.dto;

public class WorkoutExerciseDto {
    private Integer id;
    private Integer workoutSectionId;
    private Integer exerciseId;
    private Integer sets;
    private Integer repetitions;
    private Double weight;
    private Integer restTime;

    public WorkoutExerciseDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkoutSectionId() {
        return workoutSectionId;
    }

    public void setWorkoutSectionId(Integer workoutSectionId) {
        this.workoutSectionId = workoutSectionId;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
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
