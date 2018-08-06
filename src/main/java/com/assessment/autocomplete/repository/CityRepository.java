package com.assessment.autocomplete.repository;

import com.assessment.autocomplete.model.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends ElasticsearchRepository<City, String>{

  List<City> findByName(String text);

}
