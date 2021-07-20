package hu.webuni.hr.totinistvan.customExceptions;

public class EmployeeDataError {

    private String message;

    public EmployeeDataError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
