package mb.pso.issuesystem.entity.utility;

import java.util.Arrays;

public class EmailNotification {
    String[] _to;
    String subject;
    String body;
    String to;

    public EmailNotification(String[] _to, String subject, String body) {

        this._to = _to;
        this.subject = subject;
        this.body = body;
        this.to = Arrays.toString(_to);
    }

    public EmailNotification() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String[] get_to() {
        return _to;
    }

    public void set_to(String[] _to) {
        this._to = _to;
        this.to = Arrays.toString(_to);
    }

    public String getTo() {
        return to;
    }

}
