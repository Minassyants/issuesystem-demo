package mb.pso.issuesystem.entity.im;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import mb.pso.issuesystem.entity.Employee;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    private MessageContent content;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Employee author;
    @Column(updatable = false)
    @CreationTimestamp
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;
    @ManyToOne
    private Chat chat;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<MessageStatus> statuses = new HashSet<MessageStatus>();

    public Message(Integer id, MessageContent content, Employee author, LocalDateTime createdAt, Chat chat,
            Set<MessageStatus> statuses) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.chat = chat;
        this.statuses = statuses;
    }

    public Message() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Employee getAuthor() {
        return author;
    }

    public void setAuthor(Employee author) {
        this.author = author;
    }

    public Set<MessageStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<MessageStatus> status) {
        this.statuses.clear();
        this.statuses.addAll(status);
    }

    public void addToStatuses(MessageStatus status) {
        this.statuses.add(status);
    }

    

}
