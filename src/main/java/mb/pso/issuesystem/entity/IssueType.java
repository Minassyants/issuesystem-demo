package mb.pso.issuesystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//[x] REFACTOR
/**
 * Represents the way issue was recieved (e.g. e-mail, 2Gis).
 */
@Entity
public class IssueType {
    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Name of the issue type.
     */
    @Column(unique = true)
    private String name;

    public IssueType() {
    }

    public IssueType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IssueType [id=" + id + ", name=" + name + "]";
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

}
