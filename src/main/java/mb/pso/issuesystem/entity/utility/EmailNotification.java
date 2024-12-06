package mb.pso.issuesystem.entity.utility;

import io.vertx.core.json.JsonObject;

//[x] REFACTOR
/**
 * Represents an email notification with metadata for sending messages via an
 * email service.
 * <p>
 * Contains details such as recipient, subject, template name, and optional body
 * data.
 * </p>
 */
public class EmailNotification {

    private String prefix;

    private String to;

    private String templateName;

    private String subject;

    private JsonObject body;

    public EmailNotification() {
    }

    public EmailNotification(String prefix, String to, String templateName, String subject) {
        this(prefix, to, templateName, subject, null);
    }

    public EmailNotification(String prefix, String to, String templateName, String subject, JsonObject body) {
        this.prefix = prefix;
        this.to = to;
        this.templateName = templateName;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public String toString() {
        return "EmailNotification [prefix=" + prefix + ", to=" + to + ", templateName=" + templateName + ", subject="
                + subject + ", body=" + body + "]";
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

    public static class Builder {
        private String prefix;
        private String to;
        private String templateName;
        private String subject;
        private JsonObject body = new JsonObject();

        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder templateName(String templateName) {
            this.templateName = templateName;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder with(String key, Object value) {
            this.body.put(key, value);
            return this;
        }

        public EmailNotification build() {
            return new EmailNotification(prefix, to, templateName, subject, body);
        }

    }

}
