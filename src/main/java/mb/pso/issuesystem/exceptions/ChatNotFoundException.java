package mb.pso.issuesystem.exceptions;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(String message) {
        super("Chat " + message + " not found");
    }
}
