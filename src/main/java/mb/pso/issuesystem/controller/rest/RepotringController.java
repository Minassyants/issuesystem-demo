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

import mb.pso.issuesystem.dto.BasicReportRowDto;
import mb.pso.issuesystem.entity.BasicReportRow;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.ReportingService;

//[ ] REFACTOR
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

    @GetMapping
    public ResponseEntity<List<BasicReportRowDto>> getReport(
            @RequestParam Timestamp start,
            @RequestParam Timestamp end) {

        Collection<BasicReportRow> rows = reportingService.getReport(start.toLocalDateTime().toLocalDate(),
                end.toLocalDateTime().toLocalDate());

        return ResponseEntity.ok(mapper.toDtoList(rows, BasicReportRowDto.class));
    }
}
