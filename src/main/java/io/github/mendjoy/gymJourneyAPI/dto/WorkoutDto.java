package io.github.mendjoy.gymJourneyAPI.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class WorkoutDto {
    private Integer id;

    @NotNull(message = "O id do usuário deve ser informado!")
    private Integer userId;

    @NotBlank(message = "O nome do treino deve ser informado!")
    private String name;

    @NotBlank(message = "A descrição do treino deve ser informada!")
    private String description;

    @NotNull(message = "O número de sessões do treino deve ser informado!")
    @Min(value = 1, message = "O número de sessões do treino deve ser maior que zero!")
    private Integer maxSessions;

    @NotNull(message = "A data de início do treino deve ser informada!")
    private LocalDate startDate;
    private LocalDate endDate;

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
