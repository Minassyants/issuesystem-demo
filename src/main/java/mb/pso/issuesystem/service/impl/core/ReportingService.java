package mb.pso.issuesystem.service.impl.core;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.core.BasicReportRow;
import mb.pso.issuesystem.repository.core.IssueRepository;


@Service
public class ReportingService {

    private final IssueRepository issueRepository;

    public ReportingService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Collection<BasicReportRow> getReport(LocalDate start, LocalDate end) {
        end = end.plusDays(1);
        return issueRepository.fetchReport(start, end);
    }
}
