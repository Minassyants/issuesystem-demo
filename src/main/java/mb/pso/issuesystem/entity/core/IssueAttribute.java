package mb.pso.issuesystem.entity.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Represents a specific flag that describes the issue (e.g., "Ненадлежащее
 * качество товара").
 * <p>
 * This class is used to group issues by generalizing their main problem.
 * </p>
 */
@Entity
public class IssueAttribute {
    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Name of a issue attribute.
     */
    @Column(unique = true)
    private String name;

    private Boolean isDeprecated = false;

    public IssueAttribute() {
    }

    public IssueAttribute(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IssueAttribute [id=" + id + ", name=" + name + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDeprecated() {
        return isDeprecated;
    }

    public void setIsDeprecated(Boolean isDeprecated) {
        this.isDeprecated = isDeprecated;
    }

}
