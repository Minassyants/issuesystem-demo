// package mb.pso.issuesystem.config;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;

// import com.arangodb.ArangoDB.Builder;
// import com.arangodb.springframework.annotation.EnableArangoRepositories;
// import com.arangodb.springframework.config.ArangoConfiguration;

// @Configuration
// @EnableArangoRepositories(basePackages = { "mb.pso.issuesystem.repository" })
// public class ArangoConfig implements ArangoConfiguration {
//     @Value("${arangodb_host}")
//     private String arangodbHost;
//     @Value("${arangodb_port}")
//     private Integer arangodbPort;

//     @Override
//     public Builder arango() {

//         return new Builder().host(arangodbHost, arangodbPort);
//     }

//     @Override
//     public String database() {

//         return "issuesystem";
//     }

// }