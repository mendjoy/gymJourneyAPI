package io.github.mendjoy.gymJourneyAPI.exception;

public class CustomGymJourneyApiException extends RuntimeException{

    private final Integer statusCode;

    public CustomGymJourneyApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
