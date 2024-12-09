package mb.pso.issuesystem.controller.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import mb.pso.issuesystem.dto.core.EmployeeDto;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.ldap.AdUserService;


@Tag(name = "Employees", description = "Operations related to employees")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final AdUserService adUserService;
    private final DtoMapper mapper;

    public EmployeeController(AdUserService adUserService, DtoMapper mapper) {
        this.adUserService = adUserService;
        this.mapper = mapper;
    }

    @Operation(summary = "Search for available employees in Active Directory", description = "Search employees by their given name or surname.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of available employees retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/available")
    public ResponseEntity<List<EmployeeDto>> getAvailableEmployees(
            @Parameter(description = "Query for employee search", required = true) @RequestParam @NotBlank String q) {
        return ResponseEntity.ok(mapper.toDtoList(adUserService.findAllByGivenNameSn(q), EmployeeDto.class));
    }
}
