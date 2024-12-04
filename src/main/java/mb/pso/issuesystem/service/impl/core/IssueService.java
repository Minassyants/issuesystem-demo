package mb.pso.issuesystem.service.impl.core;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;

import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.QIssue;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.entity.es.IssueDocument;
import mb.pso.issuesystem.repository.CombinedRepository;
import mb.pso.issuesystem.repository.IssueRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[x] REFACTOR
@Service
public class IssueService extends AbstractCrudService<Issue, Integer> {

    private final IssueRepository repository;
    private final ElasticsearchOperations elasticsearchOperations;

    public IssueService(IssueRepository repository, ElasticsearchOperations elasticsearchOperations) {
        this.repository = repository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    protected Integer getId(Issue entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Issue, Integer> getRepository() {
        return repository;
    }

    public Page<Issue> getAllIssues(Pageable pageable, Jwt jwt, Optional<String> q,
            Optional<List<String>> searchFieldsOptional) {
        String roles = jwt.getClaimAsString("scope");
        QIssue issue = QIssue.issue;
        BooleanBuilder where = new BooleanBuilder();
        if (q.isPresent()) {
            NativeQuery query = NativeQuery.builder()
                    .withQuery(t -> t.queryString(arg0 -> arg0.fuzziness("auto").query(q.get()))).build();
            if (searchFieldsOptional.isPresent()) {
                List<String> searchFields = searchFieldsOptional.get();
                if (searchFields.size() > 0)
                    query = NativeQuery.builder().withQuery(arg0 -> arg0.multiMatch(arg1 -> arg1.fields(
                            searchFields)
                            .query(q.get()))).build();

            }

            // [ ] индекс надо в переменную выводить
            SearchHits<IssueDocument> a = elasticsearchOperations.search(query, IssueDocument.class,
                    IndexCoordinates.of("pso_issue_gzk"));
            List<Integer> w = a.map(arg0 -> arg0.getContent().getId()).toList();
            where.and(issue.id.in(w));
        }

        if (roles.contains("employee")) {
            where.and(issue.issuedEmployees.any().displayName.eq(jwt.getClaimAsString("displayname"))
                    .or(issue.chat.members.any().displayName.eq(jwt.getClaimAsString("displayname")))
                    .and(issue.status.eq(IssueStatus.INPROGRESS)));
        }

        Page<Issue> issues = repository.findAll(where, pageable);
        return issues;
    }

}
