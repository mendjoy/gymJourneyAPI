package io.github.mendjoy.gymJourneyAPI.dto.workout;

public class WorkoutExerciseDTO {
    private Integer id;
    private Integer sets;
    private Integer repetitions;
    private Double weight;
    private Integer restTime;

    public WorkoutExerciseDTO() {
    }

    public WorkoutExerciseDTO(Integer id, Integer sets, Integer repetitions, Double weight, Integer restTime) {
        this.id = id;
        this.sets = sets;
        this.repetitions = repetitions;
        this.weight = weight;
        this.restTime = restTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    }}
