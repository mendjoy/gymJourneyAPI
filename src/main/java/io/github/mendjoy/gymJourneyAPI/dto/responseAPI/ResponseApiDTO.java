package io.github.mendjoy.gymJourneyAPI.dto.responseAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseApiDTO {

    private HttpStatus status;
    private String message;
    private boolean success;
    private Object data;

    public ResponseApiDTO() {
    }

    public ResponseApiDTO(HttpStatus status, String message, boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }

    public ResponseApiDTO(HttpStatus status, boolean success, Object data) {
        this.status = status;
        this.success = success;
        this.data = data;
    }

    public static ResponseApiDTO success(HttpStatus status, String message) {
        return new ResponseApiDTO(status, message, true);
    }

    public static ResponseApiDTO success(HttpStatus status, Object data) {
        return new ResponseApiDTO(status, true, data);
    }

    public static ResponseApiDTO error(HttpStatus status, String message) {
        return new ResponseApiDTO(status, message, false);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
