package mb.pso.issuesystem.dto;

public class FileInfo {
    private String url;
    private String contentType;

    public FileInfo(String url, String contentType) {
        this.url = url;
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
