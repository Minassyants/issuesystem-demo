package mb.pso.issuesystem.dto;

import java.util.List;

public class BasicReport {
    List<RepostRow> lines;

    public BasicReport() {
    }

    class RepostRow {
        private String type;
        private String total_issues;
        private String total_closed;

        public RepostRow() {
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTotal_issues() {
            return total_issues;
        }

        public void setTotal_issues(String total_issues) {
            this.total_issues = total_issues;
        }

        public String getTotal_closed() {
            return total_closed;
        }

        public void setTotal_closed(String total_closed) {
            this.total_closed = total_closed;
        }

    }

    public List<RepostRow> getLines() {
        return lines;
    }

    public void setLines(List<RepostRow> lines) {
        this.lines = lines;
    }

}
