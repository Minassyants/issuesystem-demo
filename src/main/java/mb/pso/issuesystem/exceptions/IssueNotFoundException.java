package mb.pso.issuesystem.exceptions;


public class IssueNotFoundException extends RuntimeException {
    public IssueNotFoundException(Integer issueId) {
        super("Issue " + issueId.toString() + " not found");
    }
}
