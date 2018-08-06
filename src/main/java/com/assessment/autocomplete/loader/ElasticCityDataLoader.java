package com.assessment.autocomplete.loader;

import com.assessment.autocomplete.model.City;
import com.assessment.autocomplete.repository.CityRepository;
import com.assessment.autocomplete.utils.ElasticConstants;
import com.assessment.autocomplete.utils.LogMessages;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ElasticCityDataLoader {

  private Logger logger = Logger.getLogger(ElasticCityDataLoader.class.getName());

  private CityRepository cityRepository;
  private ElasticsearchOperations operations;
  private ResourceLoader resourceLoader;
  private String STATIC_DATA_LOCATION;

  @Autowired
  public ElasticCityDataLoader(final ElasticsearchOperations operations,
                               final CityRepository cityRepository,
                               final ResourceLoader resourceLoader,
                               @Value("${static.data.location}") final String STATIC_DATA_LOCATION) {
    this.operations = operations;
    this.cityRepository = cityRepository;
    this.resourceLoader = resourceLoader;
    this.STATIC_DATA_LOCATION = STATIC_DATA_LOCATION;
  }

  /**
   * This method will help in reading data from elastic through helper method
   * and load it into elastic search
   */
  @PostConstruct
  @Transactional
  public void loadAllCitiesIntoElastic(){

    operations.putMapping(City.class);
    logger.log(Level.INFO, LogMessages.ELASTIC_LOADING_STARTED);
    cityRepository.save(readCitiesInfoFromFile());
    logger.log(Level.INFO, LogMessages.ELASTIC_LOADING_COMPLETED);

  }

  /**
   * This method reads data from static resource and return as list of City
   * @return List of city
   */

  private List<City> readCitiesInfoFromFile() {
    LineIterator lineIterator = null;
    List<City> cities = new ArrayList<>();
    int recordCount = 0;
    try {
      logger.log(Level.INFO, LogMessages.FILE_READING_STARTED);
      File inputFile = resourceLoader.getResource(STATIC_DATA_LOCATION).getFile();
      lineIterator = FileUtils.lineIterator(inputFile, ElasticConstants.DATA_ENCODING_TYPE);
      while (lineIterator.hasNext()) {
        String line = lineIterator.nextLine();
        String[] cityInfo = line.split(ElasticConstants.DATA_SEPERATOR);
        if(recordCount > 0 && cityInfo.length == ElasticConstants.EXPECTED_LENGTH) {
          cities.add(new City(cityInfo[ElasticConstants.CITY_NAME_INDEX],
            Long.parseLong(cityInfo[ElasticConstants.PINCODE_INDEX]),
            cityInfo[ElasticConstants.CIRCLE_INDEX],
            cityInfo[ElasticConstants.TALUK_INDEX],
            cityInfo[ElasticConstants.DISTRICT_INDEX],
            cityInfo[ElasticConstants.STATE_INDEX], 0));
        }
        recordCount += 1;
      }
      logger.log(Level.INFO, LogMessages.FILE_READING_COMPLETED + recordCount);
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      LineIterator.closeQuietly(lineIterator);
    }
    return cities;
  }

}
