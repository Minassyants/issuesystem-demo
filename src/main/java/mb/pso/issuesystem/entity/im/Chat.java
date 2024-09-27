package mb.pso.issuesystem.entity.im;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Issue;

@Entity
public class Chat {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    // [ ] Fetch type lazy +
    // https://stackoverflow.com/questions/28746584/how-to-avoid-lazy-fetch-in-json-serialization-using-spring-data-jpa-spring-web
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private Issue issue;
    @ManyToMany
    private List<Employee> members = new ArrayList<>();

    public Chat() {
    }

    public Chat(Issue issue) {
        this.issue = issue;
    }

    public Chat(Integer id, Issue issue, List<Employee> members) {
        this.id = id;
        this.issue = issue;
        this.members = members;
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

    public List<Employee> getMembers() {
        return members;
    }

    public void setMembers(List<Employee> members) {
        this.members = members;
    }

}
