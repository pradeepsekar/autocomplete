package com.assessment.autocomplete.utils;

import com.assessment.autocomplete.enums.SearchQueryType;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class QueryBuilderHelper {
  private Logger logger = Logger.getLogger(QueryBuilderHelper.class.getName());


  /**
   * This method helps in building query string based on the search type given by user
   * @param queryString - Query param to search
   * @param searchQueryType - Search Query type
   * @return query string
   */
  public String buildNativeSearchParam(final String queryString, final SearchQueryType searchQueryType) {
    String searchQuery = "";
    switch (searchQueryType) {
      case EXACT_MATCH:
        searchQuery = queryString;
        break;
      case ENDS_WITH:
        searchQuery = ElasticConstants.WILD_CARD_SEARCH + queryString;
        break;
      case STARTS_WITH:
        searchQuery = queryString + ElasticConstants.WILD_CARD_SEARCH;
        break;
      case ANY_MATCH:
        searchQuery = ElasticConstants.WILD_CARD_SEARCH + queryString + ElasticConstants.WILD_CARD_SEARCH;
        break;
      default:
        break;
    }
    logger.log(Level.INFO, searchQuery);
    return searchQuery;
  }

  /**
   * This method builds sort builder object to be used in native elastic query
   *
   * @param fieldToSort - field to sort
   * @param sortOrder - Order to sort
   * @return Sort builder object to be used by native elastic query
   */
  public SortBuilder buildElasticResultsSortDetails(String fieldToSort, SortOrder sortOrder) {
    SortBuilder sortDetails = SortBuilders.fieldSort(fieldToSort);
    sortDetails.order(sortOrder);
    return sortDetails;
  }
}
