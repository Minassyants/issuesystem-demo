package mb.pso.issuesystem.controller.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.dto.IssueAttributeDto;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.IssueAttributeService;

//[ ] REFACTOR
@RestController
@RequestMapping("/issue-attributes")
public class IssueAttributeController {

    private final IssueAttributeService issueAttributeService;
    private final DtoMapper mapper;

    public IssueAttributeController(IssueAttributeService issueAttributeService, DtoMapper mapper) {
        this.issueAttributeService = issueAttributeService;
        this.mapper = mapper;
    }

    @GetMapping("/available")
    public ResponseEntity<List<IssueAttributeDto>> getAvailableIssueAttributes() {
        return ResponseEntity
                .ok(mapper.toDtoList(issueAttributeService.getAvailableIssueAttributes(), IssueAttributeDto.class));
    }

}
