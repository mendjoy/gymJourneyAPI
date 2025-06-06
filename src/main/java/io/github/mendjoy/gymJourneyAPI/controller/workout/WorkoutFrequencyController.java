package io.github.mendjoy.gymJourneyAPI.controller.workout;

import io.github.mendjoy.gymJourneyAPI.dto.responseAPI.ResponseApiDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.response.WorkoutFrequencyResponseDTO;
import io.github.mendjoy.gymJourneyAPI.service.workout.WorkoutFrequencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/workout/frequency")
public class WorkoutFrequencyController {

    private final WorkoutFrequencyService workoutFrequencyService;

    public WorkoutFrequencyController(WorkoutFrequencyService workoutFrequencyService){
        this.workoutFrequencyService = workoutFrequencyService;
    }

    @PostMapping("{idWorkout}/{date}")
    public ResponseEntity<ResponseApiDTO> registerFrequency(@PathVariable Integer idWorkout, @PathVariable LocalDate date){
        WorkoutFrequencyResponseDTO workoutFrequencyResponseDTO = workoutFrequencyService.registerFrequency(idWorkout, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApiDTO.success(HttpStatus.CREATED, workoutFrequencyResponseDTO));
    }
}
