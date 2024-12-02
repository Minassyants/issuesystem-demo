package mb.pso.issuesystem.entity.enums;

/**
 * Represents user roles within the system.
 * <p>
 * This enum is planned to be deprecated as roles and access control
 * will be managed via Active Directory in the future.
 * </p>
 */
public enum Roles {
    /** Role representing system administrators. */
    ADMIN,

    /** Role representing standard system users. */
    USER
}
