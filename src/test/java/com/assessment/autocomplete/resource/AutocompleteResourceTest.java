package com.assessment.autocomplete.resource;

import com.assessment.autocomplete.config.AutoCompleteTestConfiguration;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("unit-test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoCompleteTestConfiguration.class)
public class AutocompleteResourceTest {

  @Autowired
  AutocompleteResource autocompleteResource;

  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {

    mockMvc = MockMvcBuilders.standaloneSetup(autocompleteResource).build();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void searchByCityName() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/rest/search/name?query=Che&searchQueryType=STARTS_WITH&resultSize=25"))
    .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void addHitCount() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/rest/search/update/hitcount?cityName=Chatham"))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

}
