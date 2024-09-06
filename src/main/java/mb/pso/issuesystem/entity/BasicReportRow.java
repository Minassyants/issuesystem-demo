package mb.pso.issuesystem.entity;

public interface BasicReportRow {
     String getType();

     Integer getTotalIssues();

     Integer getTotalClosed();
     Integer getTotalClosedPct();
     Integer getTotalWorking();
     Integer getTotalWorkingPct();
     

}
