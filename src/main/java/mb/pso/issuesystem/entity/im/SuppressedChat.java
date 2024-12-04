package mb.pso.issuesystem.entity.im;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import mb.pso.issuesystem.entity.Employee;

//[x] REFACTOR
/**
 * Represents the suppression status of a chat for a specific employee.
 * <p>
 * Tracks whether a chat is suppressed for notifications.
 * </p>
 */
@Entity
public class SuppressedChat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Employee employee;

    private Integer chatId;

    private Boolean isSuppressed = false;

    public SuppressedChat() {
    }

    public SuppressedChat(Employee employee, Integer chatId, Boolean isSuppressed) {
        this(null, employee, chatId, isSuppressed);
    }

    public SuppressedChat(Integer id, Employee employee, Integer chatId, Boolean isSurpressed) {
        this.id = id;
        this.employee = employee;
        this.chatId = chatId;
        this.isSuppressed = isSurpressed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Boolean getIsSuppressed() {
        return isSuppressed;
    }

    public void setIsSuppressed(Boolean isSuppressed) {
        this.isSuppressed = isSuppressed;
    }

}
