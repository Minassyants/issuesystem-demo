package mb.pso.issuesystem.dto;

public record BasicReportRowDto(String type, Integer totalIssues, Integer totalClosed, Integer totalClosedPct,
        Integer totalWorking, Integer totalWorkingPct) {

}
