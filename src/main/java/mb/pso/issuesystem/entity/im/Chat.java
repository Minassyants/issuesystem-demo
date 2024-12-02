package mb.pso.issuesystem.entity.im;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Issue;

//[x] REFACTOR
/**
 * Represents a discussion of the issue.
 * <p>
 * The chat's ID is mapped to the corresponding issue.
 * This entity includes members participating in the discussion
 * and tracks whether the chat is closed.
 * </p>
 */
@Entity
public class Chat {

    @Id
    private Integer id;

    /**
     * Indicates whether the chat is closed.
     * Defaults to {@code false}.
     */
    private Boolean isClosed = false;

    /**
     * The issue associated with this chat.
     * Fetched lazily to optimize performance.
     * Ignored in JSON serialization to prevent recursive relationships.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private Issue issue;

    /**
     * The members of the chat.
     * Changes to this collection cascade to related entities.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Employee> members = new HashSet<>();

    public Chat() {
    }

    public Chat(Issue issue) {
        this.issue = issue;
    }

    public Chat(Integer id, Boolean isClosed, Issue issue, Set<Employee> members) {
        this.id = id;
        this.isClosed = isClosed;
        this.issue = issue;
        this.members = members;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Chat other = (Chat) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
