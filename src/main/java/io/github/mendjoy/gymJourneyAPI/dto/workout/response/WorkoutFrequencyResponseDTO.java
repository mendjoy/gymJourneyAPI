package io.github.mendjoy.gymJourneyAPI.dto.workout.response;

import java.time.LocalDate;

public class WorkoutFrequencyResponseDTO {

    private Integer id;
    private Integer userId;
    private Integer workoutId;
    private LocalDate frequencyDate;

    public WorkoutFrequencyResponseDTO() {
    }

    public WorkoutFrequencyResponseDTO(Integer id, Integer userId, Integer workoutId, LocalDate frequencyDate) {
        this.id = id;
        this.userId = userId;
        this.workoutId = workoutId;
        this.frequencyDate = frequencyDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public LocalDate getFrequencyDate() {
        return frequencyDate;
    }

    public void setFrequencyDate(LocalDate frequencyDate) {
        this.frequencyDate = frequencyDate;
    }
}
