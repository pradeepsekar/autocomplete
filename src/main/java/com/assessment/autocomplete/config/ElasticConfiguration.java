package com.assessment.autocomplete.config;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.File;
import java.io.IOException;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.assessment.autocomplete.repository")
public class ElasticConfiguration {

  String ELASTIC_DATA_LOC;

  public ElasticConfiguration(@Value("${elastic.data.location}") String elasticDataLoc) {
    this.ELASTIC_DATA_LOC = elasticDataLoc;
  }

  @Bean
  public NodeBuilder nodeBuilder() {
    return new NodeBuilder();
  }

  @Bean
  public ElasticsearchOperations elasticsearchTemplate() throws IOException {
    File tmpDir = File.createTempFile("elastic", Long.toString(System.nanoTime()));
    Settings.Builder elasticsearchSettings =
      Settings.settingsBuilder()
        .put("http.enabled", "true") // 1
        .put("index.number_of_shards", "1")
        .put("path.data", new File(tmpDir, ELASTIC_DATA_LOC).getAbsolutePath()) // 2
        .put("path.logs", new File(tmpDir, "logs").getAbsolutePath()) // 2
        .put("path.work", new File(tmpDir, "work").getAbsolutePath()) // 2
        .put("path.home", tmpDir); // 3



    return new ElasticsearchTemplate(nodeBuilder()
      .local(true)
      .settings(elasticsearchSettings.build())
      .node()
      .client());
  }
}
