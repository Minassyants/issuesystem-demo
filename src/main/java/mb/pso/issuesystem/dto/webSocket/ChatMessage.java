package mb.pso.issuesystem.dto.webSocket;

public class ChatMessage {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ChatMessage() {
    }

    public ChatMessage(Integer id) {
        this.id = id;
    }

}
