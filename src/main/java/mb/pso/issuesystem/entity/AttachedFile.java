package mb.pso.issuesystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import mb.pso.issuesystem.listeners.AttachedFileEntityListener;

@Entity
@EntityListeners(AttachedFileEntityListener.class)
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 1000)
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
