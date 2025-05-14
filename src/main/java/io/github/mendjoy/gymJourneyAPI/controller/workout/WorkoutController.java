package io.github.mendjoy.gymJourneyAPI.controller.workout;

import io.github.mendjoy.gymJourneyAPI.dto.responseAPI.ResponseApiDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.WorkoutDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.request.WorkoutRequestDTO;
import io.github.mendjoy.gymJourneyAPI.dto.workout.response.WorkoutResponseDTO;
import io.github.mendjoy.gymJourneyAPI.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/workout")
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @PostMapping("/register")
    public ResponseEntity<ResponseApiDTO> register(@RequestBody WorkoutRequestDTO workoutRequestDTO){
        WorkoutDTO newWorkoutDTO = workoutService.register(workoutRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApiDTO.success(HttpStatus.CREATED, newWorkoutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApiDTO> deleteWorkout(@PathVariable Integer id){
        workoutService.deleteWorkout(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApiDTO.success(HttpStatus.OK, "Treino excluido com sucesso!"));
    }

    @PostMapping("/finish/{id}/{endDate}")
    public ResponseEntity<ResponseApiDTO> finishWorkout(@PathVariable Integer id, @PathVariable LocalDate endDate){
        workoutService.finishWorkout(id, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApiDTO.success(HttpStatus.OK, "Treino finalizado com sucesso!"));
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<ResponseApiDTO> getAllWorkoutsByUser(@PathVariable Integer userId){
        List<WorkoutResponseDTO> workoutResponseDTOS =  workoutService.getAllWorkoutsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApiDTO.success(HttpStatus.OK, workoutResponseDTOS));
    }

    @GetMapping("/user/all/{userId}")
    public ResponseEntity<ResponseApiDTO> getAllWorkoutsByUser(){
        List<WorkoutResponseDTO> workoutResponseDTOS =  workoutService.getAllWorkoutsByUser();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApiDTO.success(HttpStatus.OK, workoutResponseDTOS));
    }

    @GetMapping("/current/{userId}")
    public ResponseEntity<ResponseApiDTO> getCurrentWorkoutByUser(@PathVariable Integer userId){
        WorkoutResponseDTO workoutResponseDTO = workoutService.getCurrentWorkoutByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApiDTO.success(HttpStatus.OK, workoutResponseDTO));
    }

   @GetMapping("/current/user")
    public ResponseEntity<ResponseApiDTO> getCurrentWorkoutByUser(){
        WorkoutResponseDTO workoutResponseDTO = workoutService.getCurrentWorkoutByUser();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApiDTO.success(HttpStatus.OK, workoutResponseDTO));
    }

}
