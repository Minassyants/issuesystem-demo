package mb.pso.issuesystem.entity.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Not implemented.
 */
@Entity
public class AdditionalAttributeType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String name;

    public AdditionalAttributeType() {
    }

    public AdditionalAttributeType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AdditionalAttributeType [id=" + id + ", name=" + name + "]";
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
