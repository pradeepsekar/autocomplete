package com.assessment.autocomplete.repository.querybuilder;

import com.assessment.autocomplete.config.AutoCompleteTestConfiguration;
import com.assessment.autocomplete.enums.SearchQueryType;
import com.assessment.autocomplete.model.City;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@ActiveProfiles("unit-test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoCompleteTestConfiguration.class)
public class CitySearchQueryBuilderTest {

  @Autowired
  private CitySearchQueryBuilder citySearchQueryBuilder;

  @Test
  public void getAllMatchingCitiesTest() throws Exception {
    List<City> citiesResult = citySearchQueryBuilder.getAllMatchingCities("Cha", SearchQueryType.STARTS_WITH, 10);
    Assert.assertEquals(2, citiesResult.size());
  }

}


