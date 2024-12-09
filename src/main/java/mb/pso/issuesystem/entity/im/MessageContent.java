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
import mb.pso.issuesystem.entity.core.AttachedFile;
import mb.pso.issuesystem.entity.core.DepartmentFeedback;


/**
 * Represents content of the message.
 * <p>
 * This class contains information about a text content and files attached to
 * the message.
 * </p>
 * <p>
 * If content is marked as Answer, then this message should be referenced in a
 * {@link DepartmentFeedback}.
 * </p>
 */
@Entity
public class MessageContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** Text content of the message. */
    @Column(length = 1000)
    private String textMessage;

    /**
     * Files attached to the message.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<AttachedFile> attachedFiles = new ArrayList<>();

    /**
     * Indicates whether message is marked as Answer and is referenced in the
     * {@link DepartmentFeedback}
     */
    private Boolean isAnswer = false;

    public MessageContent() {
    }

    public MessageContent(Integer id, String textMessage, List<AttachedFile> attachedFiles, Boolean isAnswer) {
        this.id = id;
        this.textMessage = textMessage;
        this.attachedFiles = attachedFiles;
        this.isAnswer = isAnswer;
    }

    public Integer getId() {
        return id;
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

}
