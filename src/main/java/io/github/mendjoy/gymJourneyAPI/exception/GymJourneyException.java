package io.github.mendjoy.gymJourneyAPI.exception;

import org.springframework.http.HttpStatus;

public class GymJourneyException extends RuntimeException{

    private final HttpStatus httpStatus;

    public GymJourneyException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public static GymJourneyException notFound(String message){
        return new GymJourneyException(message, HttpStatus.NOT_FOUND);
    }

}
