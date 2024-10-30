package mb.pso.issuesystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import mb.pso.issuesystem.entity.enums.NotificationPolicy;
import mb.pso.issuesystem.entity.enums.NotificationType;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @Enumerated(EnumType.STRING)
    private NotificationPolicy policy;
    private Boolean isRead = false;
    private Boolean isSent = false;
    @ManyToMany
    private Employee employee;
    private String text;
    private Integer refId;

    public Notification() {
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
