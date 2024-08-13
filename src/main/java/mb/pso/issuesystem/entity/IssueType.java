package mb.pso.issuesystem.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(indexes = @Index(columnList = "name", unique = true))
public class IssueType {
    @Id
    private String id;

    private String name;

    @Override
    public String toString() {
        return "IssueType [id=" + id + ", name=" + name + "]";
    }

    public IssueType() {
    }

    public IssueType(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
