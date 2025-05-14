package io.github.mendjoy.gymJourneyAPI.exception.custom;

import org.springframework.http.HttpStatus;

public class CustomGymJourneyApiException extends RuntimeException{

    private final HttpStatus status;

    public CustomGymJourneyApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatusCode() {
        return status;
    }
}
