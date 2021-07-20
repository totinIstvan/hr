package hu.webuni.hr.totinistvan.customExceptions;

import java.time.LocalDateTime;

public class WrongDataInputException extends RuntimeException {

    public WrongDataInputException(LocalDateTime localDateTime) {
        super("Invalid data entered: " + localDateTime);
    }

}
