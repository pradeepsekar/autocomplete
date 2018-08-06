package com.assessment.autocomplete.resource;

import com.assessment.autocomplete.enums.SearchQueryType;
import com.assessment.autocomplete.model.City;
import com.assessment.autocomplete.repository.CityRepository;
import com.assessment.autocomplete.repository.querybuilder.CitySearchQueryBuilder;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/rest/search")
public class AutocompleteResource {

  private CityRepository cityRepository;
  private CitySearchQueryBuilder citySearchQueryBuilder;

  @Autowired
  public AutocompleteResource(CityRepository cityRepository, CitySearchQueryBuilder citySearchQueryBuilder) {
        this.cityRepository = cityRepository;
        this.citySearchQueryBuilder = citySearchQueryBuilder;
  }

  /**
   * Gets search results for the query string
   *
   * @param query - Search query
   * @param searchQueryType - Query search type
   * @param resultSize - result size
   * @return results - Search results
   */
  @ApiOperation(value = "Gets search results for the query string")
  @ApiResponses({
    @ApiResponse(code = 200, message = "OK"),
    @ApiResponse(code = 400, message = "Bad Request. Please give valid request"),
    @ApiResponse(code = 500, message = "Internal server error. Something went wrong")
  })
  @GetMapping(value = "/name")
  public List<String> searchByCityName(@RequestParam final String query, @RequestParam final SearchQueryType searchQueryType, @RequestParam int resultSize) {
    return citySearchQueryBuilder.getAllMatchingCities(query, searchQueryType, resultSize)
                                 .stream()
                                 .map(City::getName)
                                 .collect(Collectors.toList());
  }

  /**
   * Updates the hit count for a city
   * @param cityName - City name
   * @return update status - True - Success, False - Failure
   */
  @ApiOperation(value = "Increments the hit count for a city by 1")
  @ApiParam(name = "cityName", required = true, value = "City name for which the hit count to be incremented")
  @ApiResponses({
    @ApiResponse(code = 200, message = "OK"),
    @ApiResponse(code = 400, message = "Bad Request. Please give valid request"),
    @ApiResponse(code = 500, message = "Internal server error. Something went wrong")
  })
  @PutMapping(value="/update/hitcount")
  public boolean addHitCount(@RequestParam final String cityName) {
    City city = cityRepository.findOne(cityName);
    if(null != city) {
      int newHitCount = city.getHits() + 1;
      city.setHits(newHitCount);
      cityRepository.save(city);
      return true;
    }
    return false;
  }

}
