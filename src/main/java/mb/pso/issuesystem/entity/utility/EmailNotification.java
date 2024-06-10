package mb.pso.issuesystem.entity.utility;

import java.util.Arrays;

public class EmailNotification {
    String[] to;
    String subject;
    String body;
    public EmailNotification(String[] to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
    public EmailNotification() {
    }
    public String[] getTo() {
        return to;
    }
    public void setTo(String[] to) {
        this.to = to;
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
    @Override
    public String toString() {
        return "EmailNotification [to=" + Arrays.toString(to) + ", subject=" + subject + ", body=" + body + "]";
    }

    
}
