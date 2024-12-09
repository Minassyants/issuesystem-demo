package mb.pso.issuesystem.service.impl.core;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.core.AdditionalAttribute;
import mb.pso.issuesystem.entity.core.Client;
import mb.pso.issuesystem.entity.core.Issue;
import mb.pso.issuesystem.entity.core.IssueAttribute;
import mb.pso.issuesystem.entity.core.IssueType;
import mb.pso.issuesystem.entity.core.Subject;
import mb.pso.issuesystem.entity.core.Vehicle;
import mb.pso.issuesystem.entity.es.IssueDocument;
import mb.pso.issuesystem.entity.es.IssueDocument.Builder;
import mb.pso.issuesystem.repository.es.IssueDocumentRepository;


@Service
public class IssueDocumentService {

    private final IssueDocumentRepository repository;

    private final ElasticsearchOperations elasticsearchOperations;

    @Value("${elasticsearch.index}")
    private String elasticIndex;

    public IssueDocumentService(IssueDocumentRepository repository, ElasticsearchOperations elasticsearchOperations) {
        this.repository = repository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public void convertAndSave(Issue issue) {
        Client client = issue.getClient();
        Subject subject = issue.getSubject();
        IssueType issueType = issue.getType();
        List<IssueAttribute> issueAttributes = issue.getIssueAttributes();
        List<AdditionalAttribute> additionalAttributes = issue.getAdditionalAttributes();

        Builder issueDocumentBuilder = new IssueDocument.Builder()
                .id(issue.getId())
                .status(issue.getStatus())
                .docDate(issue.getDocDate())
                .issueDescription(issue.getIssueDescription())
                .issuedEmployees(issue.getIssuedEmployees().stream().map(t -> t.toString()).toList())
                .departmentFeedbacks(issue.getDepartmentFeedbacks().stream().map(t -> t.getText()).toList())
                .issuedDemands(issue.getIssuedDemands())
                .issueResult(issue.getIssueResult());
        if (client != null) {
            issueDocumentBuilder
                    .clientName(client.getName())
                    .clientAddress(client.getAddress())
                    .clientEmail(client.getEmail())
                    .clientPhoneNumber(client.getPhoneNumber());
        }
        if (issueType != null) {
            issueDocumentBuilder
                    .type(issueType.getName());
        }
        if (subject != null) {
            issueDocumentBuilder
                    .subjectDescription(subject.getDescription())
                    .subjectVin(subject instanceof Vehicle ? ((Vehicle) subject).getVin() : null)
                    .subjectType(subject instanceof Vehicle ? "vehicle" : "good");
        }
        if (issueAttributes != null) {
            issueDocumentBuilder.issueAttributes(issueAttributes.stream().map(arg0 -> arg0.getName()).toList());
        }
        if (additionalAttributes != null) {
            issueDocumentBuilder.additionalAttributes(
                    additionalAttributes.stream().map(arg0 -> arg0.getStringValue()).toList());
        }

        IssueDocument issueDocument = issueDocumentBuilder.build();

        repository.save(issueDocument);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public List<Integer> searchIssueIds(String q, Optional<List<String>> searchFields) {
        NativeQuery query = buildQuery(q, searchFields);
        SearchHits<IssueDocument> searchHits = elasticsearchOperations.search(query, IssueDocument.class,
                IndexCoordinates.of(elasticIndex));
        return searchHits.map(hit -> hit.getContent().getId()).toList();

    }

    private NativeQuery buildQuery(String q, Optional<List<String>> searchFields) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(arg0 -> arg0.queryString(arg1 -> arg1.fuzziness("auto").query(q)))
                .build();

        if (searchFields.isPresent() && !searchFields.get().isEmpty()) {
            query = NativeQuery.builder()
                    .withQuery(arg0 -> arg0.multiMatch(arg1 -> arg1
                            .fields(searchFields.get())
                            .query(q)))
                    .build();
        }
        return query;
    }

}
