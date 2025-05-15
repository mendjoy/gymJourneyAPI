package io.github.mendjoy.gymJourneyAPI.dto.workout;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class WorkoutDTO {

    private Integer id;
    @NotNull(message = "O id do Usuario deve ser informado")
    private Integer userId;
    @NotBlank(message = "O Nome do Treino deve ser informado")
    private String name;
    private String description;
    @NotNull(message = "O Numero maximo de sessões do treino deve ser informado")
    private Integer maxSessions;
    @NotNull(message = "A data de inicio do treino deve ser informada")
    private LocalDate startDate;
    private LocalDate endDate;

    public WorkoutDTO() {
    }

    public WorkoutDTO(Integer id, Integer userId, String name, String description, Integer maxSessions, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.maxSessions = maxSessions;
        this.startDate = startDate;
        this.endDate   = endDate;
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
}
