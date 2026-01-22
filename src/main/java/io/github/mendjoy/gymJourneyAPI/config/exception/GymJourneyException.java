package io.github.mendjoy.gymJourneyAPI.config.exception;

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

    public static GymJourneyException alreadyExists(String message) {
        return new GymJourneyException(message, HttpStatus.CONFLICT);
    }

    public static GymJourneyException badRequest(String message) {
        return new GymJourneyException(message, HttpStatus.BAD_REQUEST);
    }

    public static GymJourneyException internalError(String message) {
        return new GymJourneyException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static GymJourneyException forbidden(String message) {
        return new GymJourneyException(message, HttpStatus.FORBIDDEN);
    }

    public static GymJourneyException conflict(String message) {
        return new GymJourneyException(message, HttpStatus.CONFLICT);
    }

    public static GymJourneyException unauthorized(String message) {
        return new GymJourneyException(message, HttpStatus.UNAUTHORIZED);
    }

}

