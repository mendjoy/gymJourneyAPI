package io.github.mendjoy.gymJourneyAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.mendjoy.gymJourneyAPI.dto.WorkoutExerciseDto;
import jakarta.persistence.*;

@Entity
@Table(name = "workout_exercise")
public class WorkoutExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "workout_section_id", nullable = false)
    @JsonBackReference
    private WorkoutSection workoutSection;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    private Integer sets;
    private Integer repetitions;
    private Double weight;

    @Column(name = "rest_time")
    private Integer restTime;

    public WorkoutExercise() {
    }

    public WorkoutExercise(WorkoutSection workoutSection, Exercise exercise, Integer sets, Integer repetitions, Double weight, Integer restTime) {
        this.workoutSection = workoutSection;
        this.exercise = exercise;
        this.sets = sets;
        this.repetitions = repetitions;
        this.weight = weight;
        this.restTime = restTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WorkoutSection getWorkoutSection() {
        return workoutSection;
    }

    public void setWorkoutSection(WorkoutSection workoutSection) {
        this.workoutSection = workoutSection;
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

    public Integer getRestTime() {
        return restTime;
    }

    public void setRestTime(Integer restTime) {
        this.restTime = restTime;
    }

    public void update(WorkoutExerciseDto workoutExerciseDto) {
        if(workoutExerciseDto.getSets() != null){
            this.sets = workoutExerciseDto.getSets();
        }
        if(workoutExerciseDto.getRepetitions() != null){
            this.repetitions = workoutExerciseDto.getRepetitions();
        }
        if(workoutExerciseDto.getWeight() != null){
            this.weight = workoutExerciseDto.getWeight();
        }
        if(workoutExerciseDto.getRestTime() != null){
            this.restTime = workoutExerciseDto.getRestTime();
        }
    }
}
