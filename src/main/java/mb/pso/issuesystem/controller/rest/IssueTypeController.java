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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mb.pso.issuesystem.dto.core.IssueTypeDto;
import mb.pso.issuesystem.entity.core.QIssueType;
import mb.pso.issuesystem.entity.core.IssueType;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.IssueTypeService;


@Tag(name = "Issue types", description = "Operations related to issue types")
@RestController
@RequestMapping("/issue-types")
public class IssueTypeController {

    private final IssueTypeService issueTypeService;
    private final DtoMapper mapper;

    public IssueTypeController(IssueTypeService issueTypeService, DtoMapper mapper) {
        this.issueTypeService = issueTypeService;
        this.mapper = mapper;
    }

    @Operation(summary = "Create a new issue type", description = "Creates a new issue type and returns the created entity.")
    @PostMapping
    public ResponseEntity<IssueTypeDto> create(@RequestBody IssueType issueType) {
        return ResponseEntity.ok(mapper.toDto(issueTypeService.create(issueType), IssueTypeDto.class));
    }

    @Operation(summary = "Get all issue types", description = "Retrieves all issue types. Optional query parameter `q` can be used to filter issue types by name.")
    @GetMapping
    public ResponseEntity<Iterable<IssueTypeDto>> getAll(
            @Parameter(description = "Filter issue types by name") @RequestParam Optional<String> q) {
        if (q.isEmpty())
            return ResponseEntity.ok(mapper.toDtoIterable(issueTypeService.getAll(), IssueTypeDto.class));
        QIssueType issueType = QIssueType.issueType;
        Predicate predicate = issueType.name.like("*".concat(q.get()).concat("*"));
        return ResponseEntity.ok(mapper.toDtoIterable(issueTypeService.getAll(predicate), IssueTypeDto.class));
    }

    @Operation(summary = "Delete an issue type", description = "Deletes an issue type by its ID.")
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID of the issue type to delete") @PathVariable Integer id) {
        issueTypeService.deleteById(id);
    }

    @Operation(summary = "Get issue type by ID", description = "Retrieves an issue type by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<IssueTypeDto> getById(
            @Parameter(description = "ID of the issue type to retrieve") @PathVariable Integer id) {
        Optional<IssueType> issueType = issueTypeService.get(id);
        if (issueType.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mapper.toDto(issueType.get(), IssueTypeDto.class));
    }

    @Operation(summary = "Update an issue type", description = "Updates an existing issue type.")
    @PatchMapping("/{id}")
    public ResponseEntity<IssueTypeDto> update(
            @Parameter(description = "Updated issue type entity") @RequestBody IssueType issueType) {
        return ResponseEntity.ok(mapper.toDto(issueTypeService.update(issueType), IssueTypeDto.class));
    }

}
