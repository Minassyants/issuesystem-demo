package mb.pso.issuesystem.exceptions;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(String message) {
        super("Message # " + message + " not found.");  
    }
}
