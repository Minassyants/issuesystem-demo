package mb.pso.issuesystem.dto.webSocket;
//[ ] REFACTOR
public class SocketMsg {

    public enum MsgType {
        NEWMESSAGE,
        ADDEDMEMBER,
        DELETEDMEMBER,
        READ,
        NEWNOTIFICATION,
        ERROR
    }

    private MsgType type;
    private Object msg;

    public SocketMsg() {
    }

    public SocketMsg(MsgType type, Object msg) {
        this.type = type;
        this.msg = msg;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

}
