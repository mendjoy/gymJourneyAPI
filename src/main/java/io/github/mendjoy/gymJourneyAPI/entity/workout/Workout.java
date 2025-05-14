package io.github.mendjoy.gymJourneyAPI.entity.workout;

import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String name;
    private String description;

    @Column(name = "max_sessions")
    private Integer maxSessions;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    List<WorkoutSection> workoutSections;

    public Workout() {
    }

    public Workout(User user, String name, String description, Integer maxSessions, Date startDate, List<WorkoutSection> workoutSections) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.maxSessions = maxSessions;
        this.startDate = startDate;
        this.workoutSections = workoutSections;
    }

    public Workout(User user, String name, String description, Integer maxSessions, Date startDate) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.maxSessions = maxSessions;
        this.startDate = startDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<WorkoutSection> getWorkoutSections() {
        return workoutSections;
    }

    public void setWorkoutSections(List<WorkoutSection> workoutSections) {
        this.workoutSections = workoutSections;
    }
}
