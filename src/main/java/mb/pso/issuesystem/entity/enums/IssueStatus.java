package mb.pso.issuesystem.entity.enums;


/**
 * Represents the status of an issue within the system.
 * <p>
 * The statuses define the lifecycle of an issue:
 * </p>
 * <ul>
 * <li><strong>NEW</strong>: The issue has been created but not yet
 * addressed.</li>
 * <li><strong>INPROGRESS</strong>: The issue is actively being worked on.</li>
 * <li><strong>PENDINGRESULT</strong>: The issue is awaiting validation or a
 * result.</li>
 * <li><strong>CLOSED</strong>: The issue has been resolved.</li>
 * </ul>
 */
public enum IssueStatus {
    /** The issue has been created but not yet addressed. */
    NEW,

    /** The issue is actively being worked on. */
    INPROGRESS,

    /** The issue is awaiting validation or a result. */
    PENDINGRESULT,

    /** The issue has been resolved. */
    CLOSED
}
