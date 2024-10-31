package mb.pso.issuesystem.entity.im;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import mb.pso.issuesystem.entity.Employee;

@Entity
public class SurpressedChat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Employee employee;
    private Integer chatId;
    private Boolean isSurpressed = false;

    public SurpressedChat() {
    }

    

    public SurpressedChat(Employee employee, Integer chatId, Boolean isSurpressed) {
        this.employee = employee;
        this.chatId = chatId;
        this.isSurpressed = isSurpressed;
    }



    public SurpressedChat(Integer id, Employee employee, Integer chatId, Boolean isSurpressed) {
        this.id = id;
        this.employee = employee;
        this.chatId = chatId;
        this.isSurpressed = isSurpressed;
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

    public Boolean getIsSurpressed() {
        return isSurpressed;
    }

    public void setIsSurpressed(Boolean isSurpressed) {
        this.isSurpressed = isSurpressed;
    }

}
