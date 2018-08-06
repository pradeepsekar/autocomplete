package com.assessment.autocomplete.loader;

import com.assessment.autocomplete.config.AutoCompleteTestConfiguration;
import com.assessment.autocomplete.model.City;
import com.assessment.autocomplete.repository.CityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("unit-test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoCompleteTestConfiguration.class)
public class ElasticCityDataLoaderTest {

  @Autowired
  ElasticCityDataLoader elasticCityDataLoader;

  @Autowired
  CityRepository cityRepository;

  @Test
  public void loadAllCitiesIntoElastic() throws Exception {
    elasticCityDataLoader.loadAllCitiesIntoElastic();
    List<City> cities = new ArrayList<>();
    cityRepository.findAll().forEach(city -> cities.add(city));
    Assert.assertEquals(11, cities.size());
  }

}
