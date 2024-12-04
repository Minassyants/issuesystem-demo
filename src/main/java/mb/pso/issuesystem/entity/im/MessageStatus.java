package mb.pso.issuesystem.entity.im;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import mb.pso.issuesystem.entity.Employee;

//[x] REFACTOR
/**
 * Represents the status of a message for a specific employee.
 * <p>
 * Tracks the read status, associated notification, and the employee to whom the
 * status belongs.
 * </p>
 */
@Entity
public class MessageStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private Message message;

    @ManyToOne(cascade = CascadeType.ALL)
    private Employee employee;

    /**
     * Read status.
     * <ul>
     * <li><strong>0</strong> - message has not been read.</li>
     * <li><strong>1</strong> - message has been read.</li>
     * </ul>
     */
    private Integer status = 0;

    /** Indicates whether email notification was sent. */
    private Boolean notificationCreated = false;

    public MessageStatus(Integer id, Message message, Employee employee, Integer status) {
        this(id, message, employee, status, false);
    }

    public MessageStatus(Integer id, Message message, Employee employee, Integer status, Boolean notificationCreated) {
        this.id = id;
        this.message = message;
        this.employee = employee;
        this.status = status;
        this.notificationCreated = notificationCreated;
    }

    public MessageStatus(Message message, Employee employee) {
        this.message = message;
        this.employee = employee;
    }

    public MessageStatus() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final MessageStatus other = (MessageStatus) obj;
        if (this.id == null || other.id == null)
            return false;
        return this.id == other.id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getNotificationCreated() {
        return notificationCreated;
    }

    public void setNotificationCreated(Boolean notificationCreated) {
        this.notificationCreated = notificationCreated;
    }

}
