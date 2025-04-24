package io.github.mendjoy.gymJourneyAPI.entity.workoutExercise;

import io.github.mendjoy.gymJourneyAPI.entity.exercise.Exercise;
import io.github.mendjoy.gymJourneyAPI.entity.workout.Workout;
import jakarta.persistence.*;

@Entity
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    private Integer sets;
    private Integer repetitions;
    private Double weight;
    private Integer rest_time;

    public WorkoutExercise() {
    }

    public WorkoutExercise(Workout workout, Exercise exercise, Integer sets, Integer repetitions, Double weight, Integer rest_time) {
        this.workout = workout;
        this.exercise = exercise;
        this.sets = sets;
        this.repetitions = repetitions;
        this.weight = weight;
        this.rest_time = rest_time;
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

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getRest_time() {
        return rest_time;
    }

    public void setRest_time(Integer rest_time) {
        this.rest_time = rest_time;
    }
}
