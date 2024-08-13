package mb.pso.issuesystem.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(indexes = @Index(columnList = "name", unique = true))
public class Department {
    @Id
    private String id;

    private String name;

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + "]";
    }

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmpty() {
        return this.id == null || this.id.isEmpty();
    }
}
