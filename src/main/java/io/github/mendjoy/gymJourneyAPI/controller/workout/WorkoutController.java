package io.github.mendjoy.gymJourneyAPI.controller.workout;

import io.github.mendjoy.gymJourneyAPI.dto.response.ResponseApiDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;
import io.github.mendjoy.gymJourneyAPI.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/workout")
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @PostMapping("/register")
    public ResponseEntity<ResponseApiDTO> register(@RequestBody WorkoutDTO workoutDTO){
        WorkoutDTO newWorkoutDTO = workoutService.register(workoutDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApiDTO.success(HttpStatus.CREATED, newWorkoutDTO));
    }
}
