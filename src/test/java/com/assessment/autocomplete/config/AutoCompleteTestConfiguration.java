package com.assessment.autocomplete.config;

import com.assessment.autocomplete.loader.ElasticCityDataLoader;
import com.assessment.autocomplete.repository.CityRepository;
import com.assessment.autocomplete.repository.querybuilder.CitySearchQueryBuilder;
import com.assessment.autocomplete.resource.AutocompleteResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@Configuration
@ComponentScan("com.assessment.autocomplete")
public class AutoCompleteTestConfiguration {

  @Autowired
  private CityRepository cityRepository;
  @Autowired
  private ElasticsearchOperations operations;
  @Autowired
  private ResourceLoader resourceLoader;
  @Autowired
  private CitySearchQueryBuilder citySearchQueryBuilder;

  private String STATIC_DATA_LOCATION;
  private String ELASTIC_DATA_LOCATION;

  @Autowired
  public AutoCompleteTestConfiguration(@Value("${static.data.location}") String STATIC_DATA_LOCATION,
                                       @Value("${elastic.data.location}") String ELASTIC_DATA_LOCATION) {
    this.STATIC_DATA_LOCATION = STATIC_DATA_LOCATION;
    this.ELASTIC_DATA_LOCATION = ELASTIC_DATA_LOCATION;
  }


  @Bean
  public ElasticConfiguration elasticConfiguration() {
    return new ElasticConfiguration(ELASTIC_DATA_LOCATION);
  }

  @Bean
  public ElasticCityDataLoader elasticCityDataLoader() {
    return new ElasticCityDataLoader(this.operations,
      this.cityRepository,
      this.resourceLoader,
      this.STATIC_DATA_LOCATION);
  }

  @Bean
  public AutocompleteResource autocompleteResource() {
    return new AutocompleteResource(cityRepository, citySearchQueryBuilder);
  }
}
