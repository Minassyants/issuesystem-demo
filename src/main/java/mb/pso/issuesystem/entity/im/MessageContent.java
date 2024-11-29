package mb.pso.issuesystem.entity.im;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import mb.pso.issuesystem.entity.AttachedFile;
//[ ] REFACTOR
@Entity
public class MessageContent  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 1000)
    private String textMessage;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AttachedFile> attachedFiles = new ArrayList<>();
    private Boolean isAnswer = false;

    public Integer getId() {
        return id;
    }

    public MessageContent() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public List<AttachedFile> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<AttachedFile> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public Boolean getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    public MessageContent(Integer id, String textMessage, List<AttachedFile> attachedFiles, Boolean isAnswer) {
        this.id = id;
        this.textMessage = textMessage;
        this.attachedFiles = attachedFiles;
        this.isAnswer = isAnswer;
    }

}
