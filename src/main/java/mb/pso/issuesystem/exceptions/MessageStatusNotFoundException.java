package mb.pso.issuesystem.exceptions;

public class MessageStatusNotFoundException extends RuntimeException {
    MessageStatusNotFoundException(String message) {
        super("Meesage status " + message + " not found.");
    }
}