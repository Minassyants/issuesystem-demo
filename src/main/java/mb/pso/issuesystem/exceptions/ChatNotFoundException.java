package mb.pso.issuesystem.exceptions;
//[ ] REFACTOR
public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(String message) {
        super("Chat " + message + " not found");
    }
}
