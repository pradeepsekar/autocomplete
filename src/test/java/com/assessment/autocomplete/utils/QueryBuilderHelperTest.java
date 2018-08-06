package com.assessment.autocomplete.utils;

import com.assessment.autocomplete.enums.SearchQueryType;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Assert;
import org.junit.Test;

public class QueryBuilderHelperTest {

  QueryBuilderHelper queryBuilderHelper = new QueryBuilderHelper();

  @Test
  public void buildNativeSearchParam_exact_match() throws Exception {
    Assert.assertEquals("che", queryBuilderHelper.buildNativeSearchParam("che", SearchQueryType.EXACT_MATCH));
  }

  @Test
  public void buildNativeSearchParam_starts_with() throws Exception {
    Assert.assertEquals("che*", queryBuilderHelper.buildNativeSearchParam("che", SearchQueryType.STARTS_WITH));
  }

  @Test
  public void buildNativeSearchParam_any_match() throws Exception {
    Assert.assertEquals("*che*", queryBuilderHelper.buildNativeSearchParam("che", SearchQueryType.ANY_MATCH));
  }

  @Test
  public void buildNativeSearchParam_ends_with() throws Exception {
    Assert.assertEquals("*che", queryBuilderHelper.buildNativeSearchParam("che", SearchQueryType.ENDS_WITH));
  }

  @Test(expected = NullPointerException.class)
  public void buildNativeSearchParam_null() {
    queryBuilderHelper.buildNativeSearchParam("che", null);
  }


  @Test
  public void buildElasticResultsSortDetails() throws Exception {
    SortBuilder sortDetails = SortBuilders.fieldSort("test");
    sortDetails.order(SortOrder.DESC);
    SortBuilder sortBuilder = queryBuilderHelper.buildElasticResultsSortDetails("test", SortOrder.DESC);
    Assert.assertEquals(sortDetails.getClass(), sortBuilder.getClass());
  }

}
