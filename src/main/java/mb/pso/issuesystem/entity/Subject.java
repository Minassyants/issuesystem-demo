package mb.pso.issuesystem.entity;



import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Vehicle.class, name = "vehicle"),
        @JsonSubTypes.Type(value = Good.class, name = "good")
})
public abstract class Subject {
    @Id
    private String id;

    private String description;

    @Override
    public String toString() {
        return "Subject [id=" + id + ", description=" + description + "]";
    }

    public Subject() {
    }

    public Subject(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
