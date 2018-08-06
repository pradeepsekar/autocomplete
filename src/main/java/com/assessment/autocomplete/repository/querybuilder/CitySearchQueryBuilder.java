package com.assessment.autocomplete.repository.querybuilder;

import com.assessment.autocomplete.enums.SearchQueryType;
import com.assessment.autocomplete.model.City;
import com.assessment.autocomplete.utils.ElasticConstants;
import com.assessment.autocomplete.utils.QueryBuilderHelper;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CitySearchQueryBuilder {

  private ElasticsearchTemplate elasticsearchTemplate;
  private QueryBuilderHelper queryBuilderHelper;

  @Autowired
  public CitySearchQueryBuilder(ElasticsearchTemplate elasticsearchTemplate, QueryBuilderHelper queryBuilderHelper) {
    this.elasticsearchTemplate = elasticsearchTemplate;
    this.queryBuilderHelper = queryBuilderHelper;
  }

  /**
   * This method fetches data from elastic search based on user query
   *
   * @param searchQuery - search query
   * @param searchQueryType - search query type
   * @param fetchSize - limit max number of results
   * @return list of cities that fit search criteria
   */
  public List<City> getAllMatchingCities(String searchQuery, SearchQueryType searchQueryType, int fetchSize) {
    // Future enhancement - this can be made configurable to support pagination
    int initialPageNumber = 0;
    QueryBuilder cityQueryBuilder =
                          QueryBuilders.boolQuery()
                                      .should(
                                        QueryBuilders.queryStringQuery(searchQuery)
                                                      .lenient(true)
                                                      .field(ElasticConstants.FIELD_NAME))
                                      .should(QueryBuilders.queryStringQuery(
                                        queryBuilderHelper.buildNativeSearchParam(searchQuery, searchQueryType))
                                      .lenient(true)
                                      .field(ElasticConstants.FIELD_NAME));

    NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                                              .withQuery(cityQueryBuilder).withPageable(new PageRequest(initialPageNumber, fetchSize))
                                              .withSort(queryBuilderHelper.buildElasticResultsSortDetails(ElasticConstants.FIELD_HITS, SortOrder.DESC))
                                              .build();

    return elasticsearchTemplate.queryForList(nativeSearchQuery, City.class);
  }
}
