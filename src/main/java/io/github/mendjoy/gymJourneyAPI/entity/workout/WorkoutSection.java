package io.github.mendjoy.gymJourneyAPI.entity.workout;

import jakarta.persistence.*;

@Entity
@Table(name = "workout_section")
public class WorkoutSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;
    private String name;
    private String description;

    public WorkoutSection() {
    }

    public WorkoutSection(Workout workout, String name, String description) {
        this.workout = workout;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
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
}
