package io.github.mendjoy.gymJourneyAPI.dto;

public class ApiResponseDto {

    private int status;
    private String message;

    public ApiResponseDto(int status, String code) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
