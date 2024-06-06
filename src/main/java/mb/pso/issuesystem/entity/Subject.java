package mb.pso.issuesystem.entity;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;

@Document("subject")
public abstract class Subject {
    @Id
    private String id;
    @ArangoId
    private String arangoId;
    private String description;

    public Subject(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Subject [id=" + id + ", arangoId=" + arangoId + ", description=" + description + "]";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
