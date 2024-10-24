package mb.pso.issuesystem.entity.im;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.Collections;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private LocalDateTime createdAt;
    @ManyToOne
    private Chat chat;

    private Set<String> seenBy = Collections.emptySet();

    public Message(Integer id, MessageContent content, Employee author, LocalDateTime createdAt, Chat chat,
            Set<String> seenBy) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.chat = chat;
        this.seenBy = seenBy;
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

    public Set<String> getSeenBy() {
        return seenBy;
    }

    public void setSeenBy(Set<String> seenBy) {
        this.seenBy.clear();
        this.seenBy.addAll(seenBy);
    }

}
