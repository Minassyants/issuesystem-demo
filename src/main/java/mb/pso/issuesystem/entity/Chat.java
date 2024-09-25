package mb.pso.issuesystem.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Chat {
    @Id
    private Integer id;
    // TODO Fetch type lazy +
    // https://stackoverflow.com/questions/28746584/how-to-avoid-lazy-fetch-in-json-serialization-using-spring-data-jpa-spring-web
    @OneToOne
    private Issue issue;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;

    public Chat(Integer id, Issue issue, List<Message> messages) {
        this.id = id;
        this.issue = issue;
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
