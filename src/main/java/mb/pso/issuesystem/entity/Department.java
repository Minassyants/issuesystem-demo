package mb.pso.issuesystem.entity;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.PersistentIndexed;

@Document("department")
public class Department {
    @Id
    private String id;
    @ArangoId
    private String arangoId;
    @PersistentIndexed(deduplicate = true, unique = true)
    private String name;

    @Override
    public String toString() {
        return "Department [id=" + id + ", arangoId=" + arangoId + ", name=" + name + "]";
    }

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getArangoId() {
        return arangoId;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArangoId(String arangoId) {
        this.arangoId = arangoId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmpty()
    {
        return this.id == null || this.id.isEmpty();
    }
}
