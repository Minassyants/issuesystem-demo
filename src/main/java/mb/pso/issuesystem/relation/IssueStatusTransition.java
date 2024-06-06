package mb.pso.issuesystem.relation;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;

import mb.pso.issuesystem.entity.IssueStatus;

@Edge
public class IssueStatusTransition {
    @Id
    private String id;
    @From
    private IssueStatus from;
    @To
    private IssueStatus to;

    public IssueStatusTransition() {
    }

    public IssueStatusTransition(IssueStatus from, IssueStatus to) {
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IssueStatus getFrom() {
        return from;
    }

    public void setFrom(IssueStatus from) {
        this.from = from;
    }

    public IssueStatus getTo() {
        return to;
    }

    public void setTo(IssueStatus to) {
        this.to = to;
    }

}
