package mb.pso.issuesystem.entity.core;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import mb.pso.issuesystem.entity.enums.NotificationPolicy;
import mb.pso.issuesystem.entity.enums.NotificationType;
import mb.pso.issuesystem.listeners.NotificationEntityListener;


/**
 * Represents a notification entity used for delivering information to
 * employees.
 * <p>
 * Notifications contain details such as type, policy, recipient, read/sent
 * status,
 * and an optional reference to a related entity (e.g., issue or chat).
 * </p>
 * 
 * <p>
 * Notifications are automatically timestamped upon creation.
 * </p>
 */
@Entity
@EntityListeners(NotificationEntityListener.class)
public class Notification {
    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * The type of notification, defining its context (e.g. newIssue,
     * issueStatusChanged, chatClosed).
     */
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    /**
     * The delivery policy for the notification (e.g. ONLYINAPP, ONLYMAIL).
     */
    @Enumerated(EnumType.STRING)
    private NotificationPolicy policy;

    /**
     * Indicates whether the notification has been read by the recipient.
     */
    private Boolean isRead = false;

    /**
     * Indicates whether the notification has been sent to the recipient via mail.
     */
    private Boolean isSent = false;

    /**
     * Recipient of the notification.
     */
    @ManyToOne
    private Employee employee;

    /**
     * Timestamp marking when the notification was created.
     */
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    /**
     * Content of the notification.
     */
    private String text;

    /**
     * Reference ID to an issue or chat, associated with the notification.
     */
    private Integer refId;

    public Notification() {
    }

    public Notification(NotificationType type, NotificationPolicy policy, Employee employee, String text,
            Integer refId) {
        this.type = type;
        this.policy = policy;
        this.employee = employee;
        this.text = text;
    }

    public Notification(Integer id, NotificationType type, NotificationPolicy policy, Boolean isRead, Boolean isSent,
            Employee employee, String text, Integer refId) {
        this.id = id;
        this.type = type;
        this.policy = policy;
        this.isRead = isRead;
        this.isSent = isSent;
        this.employee = employee;
        this.text = text;
        this.refId = refId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public NotificationPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(NotificationPolicy policy) {
        this.policy = policy;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsSent() {
        return isSent;
    }

    public void setIsSent(Boolean isSent) {
        this.isSent = isSent;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

}
