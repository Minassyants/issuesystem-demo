package mb.pso.issuesystem.controller.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.dto.EmployeeDto;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.EmployeeService;
import mb.pso.issuesystem.service.impl.ldap.AdUserService;

//[ ] REFACTOR
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AdUserService adUserService;
    private final DtoMapper mapper;

    public EmployeeController(EmployeeService employeeService, AdUserService adUserService, DtoMapper mapper) {
        this.employeeService = employeeService;
        this.adUserService = adUserService;
        this.mapper = mapper;
    }

    @GetMapping("/available")
    public ResponseEntity<List<EmployeeDto>> getAvailableEmployees(@RequestParam String q) {
        return ResponseEntity.ok(mapper.toDtoList(adUserService.findAllByGivenNameSn(q), EmployeeDto.class));
    }
}
