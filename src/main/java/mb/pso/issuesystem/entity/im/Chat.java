package mb.pso.issuesystem.entity.im;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import mb.pso.issuesystem.entity.Issue;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    // [ ] Fetch type lazy +
    // https://stackoverflow.com/questions/28746584/how-to-avoid-lazy-fetch-in-json-serialization-using-spring-data-jpa-spring-web
    @OneToOne
    private Issue issue;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public Chat() {
    }

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
