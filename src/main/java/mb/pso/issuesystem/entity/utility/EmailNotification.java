package mb.pso.issuesystem.entity.utility;

import io.vertx.core.json.JsonObject;
//[ ] REFACTOR
public class EmailNotification {

    private String prefix;
    private String to;
    private String templateName;
    private String subject;
    private JsonObject body;

    public EmailNotification() {
    }

    public EmailNotification(String prefix, String to, String templateName, String subject) {
        this.prefix = prefix;
        this.to = to;
        this.templateName = templateName;
        this.subject = subject;
    }

    public EmailNotification(String prefix, String to, String templateName, String subject, JsonObject body) {
        this.prefix = prefix;
        this.to = to;
        this.templateName = templateName;
        this.subject = subject;
        this.body = body;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public JsonObject getBody() {
        return body;
    }

    public void setBody(JsonObject body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "EmailNotification [prefix=" + prefix + ", to=" + to + ", templateName=" + templateName + ", subject="
                + subject + ", body=" + body + "]";
    }

}
