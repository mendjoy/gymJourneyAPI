package io.github.mendjoy.gymJourneyAPI.dto;

import java.time.LocalDate;
import java.util.List;

public class WorkoutDto {
    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private Integer maxSessions;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<WorkoutSectionDto> workoutSections;

    public WorkoutDto() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxSessions() {
        return maxSessions;
    }

    public void setMaxSessions(Integer maxSessions) {
        this.maxSessions = maxSessions;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<WorkoutSectionDto> getWorkoutSections() {
        return workoutSections;
    }

    public void setWorkoutSections(List<WorkoutSectionDto> workoutSections) {
        this.workoutSections = workoutSections;
    }
}
