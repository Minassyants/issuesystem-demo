package mb.pso.issuesystem.controller.rest;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.entity.QIssueType;
import mb.pso.issuesystem.service.impl.core.IssueTypeService;
//[ ] REFACTOR
@RestController
@RequestMapping("/api/issuetype")
public class IssueTypeController {

    private final IssueTypeService issueTypeService;

    public IssueTypeController(IssueTypeService issueTypeService) {
        this.issueTypeService = issueTypeService;
    }

    @PostMapping
    public ResponseEntity<IssueType> create(@RequestBody IssueType issueType) {
        return ResponseEntity.ok(issueTypeService.create(issueType));
    }

    @GetMapping
    public ResponseEntity<Iterable<IssueType>> getAll(@RequestParam Optional<String> q) {
        if (q.isEmpty())
            return ResponseEntity.ok(issueTypeService.getAll());
        QIssueType issueType = QIssueType.issueType;
        Predicate predicate = issueType.name.like("*".concat(q.get()).concat("*"));
        return ResponseEntity.ok(issueTypeService.getAll(predicate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {

            issueTypeService.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueType> getById(@PathVariable Integer id) {
        Optional<IssueType> issueType = issueTypeService.get(id);
        if (issueType.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(issueType.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<IssueType> update(@RequestBody IssueType issueType) {
        return ResponseEntity.ok(issueTypeService.update(issueType));
    }

}
