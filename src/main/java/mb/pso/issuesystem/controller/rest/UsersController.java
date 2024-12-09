package mb.pso.issuesystem.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mb.pso.issuesystem.dto.core.UsersDto;
import mb.pso.issuesystem.entity.core.Users;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.UserService;


@Tag(name = "Users", description = "Operations related to user managment")
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final DtoMapper mapper;

    public UsersController(UserService userService, DtoMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Operation(summary = "Creates a new user", description = "Creates a new user and returns created entity")
    @PostMapping
    public ResponseEntity<UsersDto> create(@RequestBody Users user) {
        return ResponseEntity.ok(mapper.toDto(userService.create(user), UsersDto.class));
    }

    @Operation(summary = "Deletes a user", description = "Deletes user by its ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        userService.deleteById(id);
    }

    @Operation(summary = "Get all users", description = "Retrieves all users")
    @GetMapping
    public ResponseEntity<Iterable<UsersDto>> getAll() {
        return ResponseEntity.ok(mapper.toDtoIterable(userService.getAll(), UsersDto.class));
    }
}
