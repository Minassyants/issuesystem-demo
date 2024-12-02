package mb.pso.issuesystem.entity.enums;

//[ ] REFACTOR
/**
 * Defines the notification delivery policies within the system.
 * <strong>Not yet implemented.</strong>
 * <p>
 * These policies specify how notifications should be sent:
 * </p>
 * <ul>
 * <li><strong>ONLYINAPP</strong>: Notification is displayed only within the
 * application.</li>
 * <li><strong>INAPP</strong>: Notification is displayed in the application if
 * the user is online. Notification is sent via email otherwise.</li>
 * <li><strong>BOTH</strong>: Notification is sent both in the application and
 * via email.</li>
 * <li><strong>ONLYMAIL</strong>: Notification is sent only via email.</li>
 * </ul>
 */
public enum NotificationPolicy {
    /**
     * Notification is displayed only within the
     * application.
     */
    ONLYINAPP,

    /**
     * Notification is displayed in the application if
     * the user is online. Notification is sent via email otherwise.
     */
    INAPP,

    /**
     * Notification is sent both in the application and
     * via email.
     */
    BOTH,

    /** Notification is sent only via email. */
    ONLYMAIL
}
