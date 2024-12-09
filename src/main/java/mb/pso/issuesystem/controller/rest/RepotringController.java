package mb.pso.issuesystem.controller.rest;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mb.pso.issuesystem.dto.core.BasicReportRowDto;
import mb.pso.issuesystem.entity.core.BasicReportRow;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.ReportingService;


@Tag(name = "Reports", description = "Endpoints for generating and retrieving reports")
@RestController
@RequestMapping("/reports")
@PreAuthorize("hasAuthority('operator')")
public class RepotringController {

    private final ReportingService reportingService;
    private final DtoMapper mapper;

    public RepotringController(ReportingService reportingService, DtoMapper mapper) {
        this.reportingService = reportingService;
        this.mapper = mapper;
    }

    @Operation(summary = "Generate a report", description = "Generates a report for the specified date range.")
    @GetMapping
    public ResponseEntity<List<BasicReportRowDto>> getReport(
            @Parameter(description = "Start timestamp of the report period", example = "2024-01-01T00:00:00") @RequestParam Timestamp start,
            @Parameter(description = "End timestamp of the report period", example = "2024-01-31T23:59:59") @RequestParam Timestamp end) {

        Collection<BasicReportRow> rows = reportingService.getReport(start.toLocalDateTime().toLocalDate(),
                end.toLocalDateTime().toLocalDate());

        return ResponseEntity.ok(mapper.toDtoList(rows, BasicReportRowDto.class));
    }
}
