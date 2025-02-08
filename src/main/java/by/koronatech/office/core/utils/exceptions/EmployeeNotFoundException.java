package by.koronatech.office.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super();
    }
    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public EmployeeNotFoundException(String message) {
        super(message);
    }
    public EmployeeNotFoundException(Throwable cause) {
        super(cause);
    }
}