package io.github.mendjoy.gymJourneyAPI.dto.workout;

import java.util.Date;
import java.util.List;

public class WorkoutDTO {
    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private Integer maxSessions;
    private Date startDate;

    public WorkoutDTO() {
    }

    public WorkoutDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, Date startDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.maxSessions = maxSessions;
        this.startDate = startDate;
    }

    public WorkoutDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, List<WorkoutSectionDTO> workoutSectionDTOS) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.maxSessions = maxSessions;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
