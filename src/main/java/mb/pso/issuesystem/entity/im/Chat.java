package mb.pso.issuesystem.entity.im;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    private Boolean isClosed = false;
    // [ ] Fetch type lazy +
    // https://stackoverflow.com/questions/28746584/how-to-avoid-lazy-fetch-in-json-serialization-using-spring-data-jpa-spring-web
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private Issue issue;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Employee> members = new HashSet<Employee>();

    public Chat() {
    }

    public Chat(Issue issue) {
        this.issue = issue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Chat other = (Chat) obj;
        return this.id.equals(other.id);
    }

    public Chat(Integer id, Boolean isClosed, Issue issue, Set<Employee> members) {
        this.id = id;
        this.isClosed = isClosed;
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

    public Set<Employee> getMembers() {
        return members;
    }

    public void setMembers(Set<Employee> members) {
        this.members.clear();
        if (members != null)
            this.members.addAll(members);
    }

    public void addToMembers(Collection<Employee> members) {
        this.members.addAll(members);
    }

    public void addToMembers(Employee member) {
        this.members.add(member);
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

}
