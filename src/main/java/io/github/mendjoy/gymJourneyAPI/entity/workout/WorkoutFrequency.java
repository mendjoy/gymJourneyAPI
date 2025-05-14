package io.github.mendjoy.gymJourneyAPI.entity.workout;

import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "workout_frequency")
public class WorkoutFrequency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Column(name = "frequency_date")
    private LocalDate frequencyDate;

    public WorkoutFrequency() {
    }

    public WorkoutFrequency(User user, Workout workout, LocalDate frequencyDate) {
        this.user = user;
        this.workout = workout;
        this.frequencyDate = frequencyDate;
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

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public LocalDate getFrequencyDate() {
        return frequencyDate;
    }

    public void setFrequencyDate(LocalDate frequencyDate) {
        this.frequencyDate = frequencyDate;
    }
}
