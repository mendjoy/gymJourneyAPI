package io.github.mendjoy.gymJourneyAPI.exception;

import io.github.mendjoy.gymJourneyAPI.dto.responseAPI.ResponseApiDTO;
import io.github.mendjoy.gymJourneyAPI.exception.custom.CustomGymJourneyApiException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApiDTO> handleException(Exception exception){
        HttpStatus status  = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = exception.getMessage();

        if(exception instanceof UsernameNotFoundException || exception instanceof EntityNotFoundException){
            status = HttpStatus.NOT_FOUND;
        }else if(exception instanceof BadCredentialsException || exception instanceof AccessDeniedException){
            status = HttpStatus.UNAUTHORIZED;
        }else if(exception instanceof MethodArgumentNotValidException){
            message = getMessageValidator((MethodArgumentNotValidException) exception);
            status = HttpStatus.BAD_REQUEST;
        }
        else {
            message = "Ocorreu um erro interno. Tente novamente mais tarde.";
        }

        ResponseApiDTO responseApi = ResponseApiDTO.error(status, message);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @ExceptionHandler(CustomGymJourneyApiException.class)
    public ResponseEntity<ResponseApiDTO> handleCustomGymJourneyApiException(CustomGymJourneyApiException exception){
        ResponseApiDTO responseApi = ResponseApiDTO.error(exception.getStatusCode(), exception.getMessage());
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    private String getMessageValidator(MethodArgumentNotValidException exception){
        return exception.getBindingResult().getFieldErrors()
                 .stream()
                 .map(e -> e.getField() + ":" + e.getDefaultMessage())
                 .collect(Collectors.joining(";"));
    }
}
