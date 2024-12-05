package mb.pso.issuesystem.service.impl.core;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.BasicReportRow;
import mb.pso.issuesystem.repository.core.IssueRepository;

//[ ] REFACTOR
@Service
public class ReportingService {

    private final IssueRepository issueRepository;

    public ReportingService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Iterable<BasicReportRow> getReport(LocalDate start, LocalDate end) {
        end = end.plusDays(1);
        return issueRepository.fetchReport(start, end);
    }
}
