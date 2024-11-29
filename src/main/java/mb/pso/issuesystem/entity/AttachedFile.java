package mb.pso.issuesystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import mb.pso.issuesystem.listeners.AttachedFileEntityListener;

//[x] REFACTOR
/**
 * Represents a file attached to the issue.
 */
@Entity
@EntityListeners(AttachedFileEntityListener.class)
public class AttachedFile {

    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Path to the file
     */
    @Column(length = 1000)
    @Size(max = 1000)
    private String filePath;

    public AttachedFile(Integer id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    public AttachedFile() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
