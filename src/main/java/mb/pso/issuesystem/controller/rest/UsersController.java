package mb.pso.issuesystem.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UsersController {
    private final UserServiceImpl userServiceImpl;

    public UsersController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Users> create(@RequestBody Users user) {
        return ResponseEntity.ok(userServiceImpl.create(user));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        userServiceImpl.deleteById(id);
    }

    @GetMapping
    public ResponseEntity<Iterable<Users>> getAll() {
        return ResponseEntity.ok(userServiceImpl.getAll());
    }
}
