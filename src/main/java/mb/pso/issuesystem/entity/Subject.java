package mb.pso.issuesystem.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
//[ ] REFACTOR
/**
 * Represents subject of a issue.
 * <p>
 * This abstract class contains basic information about subject and can be extended with more detailed information (such as VIN or part article).
 * </p>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Vehicle.class, name = "vehicle"),
        @JsonSubTypes.Type(value = Good.class, name = "good")
})
public abstract class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 2000)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
