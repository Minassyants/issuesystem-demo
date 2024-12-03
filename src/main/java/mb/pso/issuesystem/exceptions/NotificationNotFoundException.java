package mb.pso.issuesystem.exceptions;

//[x] REFACTOR
public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(String message) {
        super(message);
    }
}