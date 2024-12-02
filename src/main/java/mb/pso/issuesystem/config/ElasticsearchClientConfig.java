package mb.pso.issuesystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//[x] REFACTOR
@Configuration
@EnableElasticsearchRepositories(basePackages = "mb.pso.issuesystem.entity.es")
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {
    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private int port;

    @Override
    public ClientConfiguration clientConfiguration() {
        String connectionString = String.format("%s:%d", host, port);
        return ClientConfiguration.builder()
                .connectedTo(connectionString)
                .build();
    }

}
