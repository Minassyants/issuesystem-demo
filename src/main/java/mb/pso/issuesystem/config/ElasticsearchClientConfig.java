package mb.pso.issuesystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//[ ] REFACTOR
@Configuration
@EnableElasticsearchRepositories(basePackages = "mb.pso.issuesystem.entity.es")
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder().connectedTo("192.168.50.18:7000").build();
    }

}
