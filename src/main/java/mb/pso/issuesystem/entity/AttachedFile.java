package mb.pso.issuesystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AttachedFile {
    @Id
    private Integer id;
    private String url;

    public AttachedFile(Integer id, String url) {
        this.id = id;
        this.url = url;
    }

    public AttachedFile() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
