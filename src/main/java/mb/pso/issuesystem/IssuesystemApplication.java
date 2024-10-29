package mb.pso.issuesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class IssuesystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssuesystemApplication.class, args);
	}

}
