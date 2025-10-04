package io.github.mendjoy.gymJourneyAPI.exception;

import io.github.mendjoy.gymJourneyAPI.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GymJourneyException.class)
    public ResponseEntity<ApiResponseDto> handleGymJourneyException(GymJourneyException ex) {
       ApiResponseDto response = new ApiResponseDto(ex.getHttpStatus().value(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleGenericException(Exception ex) {
        ApiResponseDto response = new ApiResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro inesperado. Tente novamente mais tarde.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
