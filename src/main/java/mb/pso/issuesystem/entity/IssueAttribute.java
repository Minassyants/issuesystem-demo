package mb.pso.issuesystem.entity;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.PersistentIndexed;

@Document("issueAttribute")
public class IssueAttribute {
    @Id
    private String id;
    @ArangoId
    private String arangoId;
    @PersistentIndexed(deduplicate = true, unique = true)
    private String name;

    @Override
    public String toString() {
        return "IssueAttribute [id=" + id + ", arangoId=" + arangoId + ", name=" + name + "]";
    }

    public IssueAttribute() {
    }

    public IssueAttribute(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArangoId() {
        return arangoId;
    }

    public void setArangoId(String arangoId) {
        this.arangoId = arangoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
