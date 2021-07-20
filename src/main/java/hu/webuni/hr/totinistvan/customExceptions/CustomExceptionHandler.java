package hu.webuni.hr.totinistvan.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(WrongDataInputException.class)
    public ResponseEntity<EmployeeDataError> handleWrongData(WrongDataInputException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new EmployeeDataError(e.getMessage()));
    }
}
