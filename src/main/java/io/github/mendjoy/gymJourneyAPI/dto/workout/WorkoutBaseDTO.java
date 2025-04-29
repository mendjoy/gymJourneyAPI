package io.github.mendjoy.gymJourneyAPI.dto.workout;

public class WorkoutBaseDTO {
    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private Integer maxSessions;

    public WorkoutBaseDTO() {
    }

    public WorkoutBaseDTO(Integer id, Integer userId, String name, String description, Integer maxSessions) {
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
}
