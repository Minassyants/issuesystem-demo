package mb.pso.issuesystem.exceptions;
//[ ] REFACTOR
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {
        super("Employee " + message + " not found");
    }
}
