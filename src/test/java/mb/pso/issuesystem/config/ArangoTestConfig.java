package mb.pso.issuesystem.config;

import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.arangodb.ArangoDB.Builder;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;

@SpringJUnitConfig
@EnableArangoRepositories(basePackages = { "mb.pso.issuesystem.repository" })
public class ArangoTestConfig implements ArangoConfiguration {

    @Override
    public Builder arango() {

        return new Builder().host("localhost", 8529);
    }

    @Override
    public String database() {

        return "issuesystemtest";
    }

}