package mb.pso.issuesystem.config;

import org.springframework.context.annotation.Configuration;

import com.arangodb.ArangoDB.Builder;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;

@Configuration
@EnableArangoRepositories(basePackages = { "mb.pso.issuesystem.repository" })
public class ArangoConfig implements ArangoConfiguration {

    @Override
    public Builder arango() {

        return new Builder().host("localhost", 8529);
    }

    @Override
    public String database() {

        return "ticketsystem";
    }

}