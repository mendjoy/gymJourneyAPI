package io.github.mendjoy.gymJourneyAPI.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Positive
    @Column(name = "max_sessions", nullable = false)
    private Integer maxSessions;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WorkoutSection> workoutSections = new ArrayList<>();

    public Workout() {
    }

    public Workout(User user, String name, String description, Integer maxSessions,
                   LocalDate startDate, List<WorkoutSection> workoutSections) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.maxSessions = maxSessions;
        this.startDate = startDate;
        this.workoutSections = workoutSections != null ? workoutSections : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<WorkoutSection> getWorkoutSections() {
        return workoutSections;
    }

    public void setWorkoutSections(List<WorkoutSection> workoutSections) {
        this.workoutSections = workoutSections;
    }

    public void addWorkoutSection(WorkoutSection section) {
        workoutSections.add(section);
        section.setWorkout(this);
    }

    public void updateWorkout(WorkoutDto workoutDto) {
        if (workoutDto.name() != null && !workoutDto.name().isBlank()) {
            this.setName(workoutDto.name());
        }
        if (workoutDto.description() != null && !workoutDto.description().isBlank()) {
            this.setDescription(workoutDto.description());
        }
        if (workoutDto.maxSessions() != null && workoutDto.maxSessions() > 0) {
            this.setMaxSessions(workoutDto.maxSessions());
        }
        if (workoutDto.startDate() != null) {
            this.setStartDate(workoutDto.startDate());
        }
    }
}