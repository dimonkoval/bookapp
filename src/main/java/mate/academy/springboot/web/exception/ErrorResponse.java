package mate.academy.springboot.web.exception;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> errors;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, List<String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.errors = errors;
    }
}
