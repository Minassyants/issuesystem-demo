package mb.pso.issuesystem.dto.core;

import java.util.List;

public record UsersDto(Integer id, String username, String password, String sAMAccountName, List<String> roles) {

}
