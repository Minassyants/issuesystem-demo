package mb.pso.issuesystem.entity.enums;

//[x] REFACTOR
/**
 * Defines the types of notifications that can be sent within the system.
 * <p>
 * Each notification type represents a specific event or action in the system
 * that triggers a notification.
 * </p>
 * <ul>
 * <li><strong>newIssue</strong>: A new issue has been created.</li>
 * <li><strong>issueStatusChanged</strong>: The status of an issue has been
 * updated.</li>
 * <li><strong>chatClosed</strong>: A chat associated with an issue has been
 * closed.</li>
 * <li><strong>employeeAddedToIssue</strong>: An employee has been assigned to
 * an issue.</li>
 * <li><strong>departmentFeedbackAddedToIssue</strong>: New feedback from a
 * department has been added to an issue.</li>
 * <li><strong>internalInfoChanged</strong>: Internal information related to an
 * issue has been updated.</li>
 * <li><strong>resultAdded</strong>: A result has been added to the issue.</li>
 * <li><strong>employeeAddedToChat</strong>: An employee has been added to a
 * chat.</li>
 * <li><strong>newMessageToChat</strong>: A new message has been sent to a
 * chat.</li>
 * </ul>
 */
public enum NotificationType {
    /** A new issue has been created. */
    newIssue,

    /** The status of an issue has been updated. */
    issueStatusChanged,

    /** A chat associated with an issue has been closed. */
    chatClosed,

    /** An employee has been assigned to an issue. */
    employeeAddedToIssue,

    /** New feedback from a department has been added to an issue. */
    departmentFeedbackAddedToIssue,

    /** Internal information related to an issue has been updated. */
    internalInfoChanged,

    /** A result has been added to the issue. */
    resultAdded,

    /** An employee has been added to a chat. */
    employeeAddedToChat,

    /** A new message has been sent to a chat. */
    newMessageToChat
}
