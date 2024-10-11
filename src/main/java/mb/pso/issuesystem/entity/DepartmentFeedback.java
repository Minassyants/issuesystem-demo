package mb.pso.issuesystem.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import mb.pso.issuesystem.entity.im.Message;

@Entity
public class DepartmentFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Employee author;
    @OneToOne(optional = true)
    private Message message;
    @Column(length = 2000)
    private String feedback;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AttachedFile> attachedFiles = new ArrayList<>();

    public DepartmentFeedback() {
    }

    public DepartmentFeedback(Integer id, Employee author, Message message, String feedback,
            List<AttachedFile> attachedFiles) {
        this.id = id;
        this.author = author;
        this.message = message;
        this.feedback = feedback;
        this.attachedFiles = attachedFiles;
    }

    public String getText() {
        if (message != null)
            return message.getContent().getTextMessage();
        return this.feedback;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void addAttachedFile(AttachedFile file) {
        this.attachedFiles.add(file);
    }

    public void addAllAttachedFile(List<AttachedFile> files) {
        this.attachedFiles.addAll(files);
    }

    public List<AttachedFile> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<AttachedFile> attachedFiles) {
        this.attachedFiles.clear();
        this.attachedFiles.addAll(attachedFiles);
    }

    public Employee getAuthor() {
        return author;
    }

    public void setAuthor(Employee author) {
        this.author = author;
    }
}