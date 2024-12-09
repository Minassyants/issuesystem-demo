package mb.pso.issuesystem.dto.core;

public record BasicReportRowDto(String type, Integer totalIssues, Integer totalClosed, Integer totalClosedPct,
        Integer totalWorking, Integer totalWorkingPct) {

}
