package mb.pso.issuesystem.entity.core;


/**
 * Represents a single line of the report.
 * 
 * @see mb.pso.issuesystem.repository.core.IssueRepository#fetchReport
 */
public interface BasicReportRow {

     String getType();

     Integer getTotalIssues();

     Integer getTotalClosed();

     Integer getTotalClosedPct();

     Integer getTotalWorking();

     Integer getTotalWorkingPct();

}
