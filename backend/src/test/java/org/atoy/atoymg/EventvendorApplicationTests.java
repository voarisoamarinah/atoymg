package org.atoy.atoymg;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.nio.charset.StandardCharsets;
import org.atoy.atoymg.models.Eventvendor;
import org.atoy.atoymg.services.interfaces.EventvendorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.jdbc.core.JdbcTemplate;
import org.atoy.atoymg.utils.ConstraintDisabler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class EventvendorApplicationTests {

  private MockMvc mockMvc;
  private EventvendorService service;
  private Eventvendor mockData=new Eventvendor();
  private final JdbcTemplate jdbcTemplate;
  private ConstraintDisabler constraintDisabler;

  @Autowired
  public EventvendorApplicationTests(MockMvc mockMvc, EventvendorService service, JdbcTemplate jdbcTemplate) throws Exception {
    this.mockMvc=mockMvc;
    this.service=service;
    this.jdbcTemplate = jdbcTemplate;
    constraintDisabler = new ConstraintDisabler(jdbcTemplate);
  }

  private final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
  MediaType.APPLICATION_JSON.getSubtype(),
  StandardCharsets.UTF_8);

   
  @BeforeEach
  void seedingTheDatabase(){
    constraintDisabler.disableConstraintsForTable("eventvendors");
    mockData = new Eventvendor();
    mockData.setAgreedprice(0.);
    mockData.setNotes("mock");
    mockData.setCreatedat(java.time.OffsetDateTime.now());
    mockData.setUpdatedat(java.time.OffsetDateTime.now());
    
    service.createEventvendor(mockData);
  }
  @AfterEach
  void removingMockdatas(TestInfo testInfo){
    if(testInfo.getTags().contains("skipAfter")){
      return;
    }
    service.deleteEventvendor(mockData.getId());
    constraintDisabler.restoreConstraints();
  }
  

  @Test
  void testSearchEventvendor() throws Exception{
    ObjectMapper mapper=new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter writer=mapper.writer().withDefaultPrettyPrinter();
    String requestJson=writer.writeValueAsString(mockData);
    MockHttpServletResponse resp=mockMvc.perform(post("/eventvendors/search").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

  @Test
  void testGetAllEventvendor() throws Exception{
    MockHttpServletResponse resp=mockMvc.perform(get("/eventvendors")).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

   
  @Test
  void testCreateEventvendor() throws Exception{
    Eventvendor otherMockData=new Eventvendor();
    otherMockData.setAgreedprice(0.);
    otherMockData.setNotes("mock");
    otherMockData.setCreatedat(java.time.OffsetDateTime.now());
    otherMockData.setUpdatedat(java.time.OffsetDateTime.now());
    
    service.createEventvendor(otherMockData);
    MockHttpServletResponse resp=mockMvc.perform(get("/eventvendors")).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
    service.deleteEventvendor(otherMockData.getId());
  }
  

   
  @Test
  void testModifyEventvendor() throws Exception{
    ObjectMapper mapper=new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter writer=mapper.writer().withDefaultPrettyPrinter();
    String requestJson=writer.writeValueAsString(mockData);
    String requestUrl="/eventvendors/%s";
    requestUrl=String.format(requestUrl, mockData.getId());
    MockHttpServletResponse resp=mockMvc.perform(put(requestUrl).contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

  @Test
  @Tag("skipAfter")
  void testDeleteEventvendor() throws Exception{
    service.deleteEventvendor(mockData.getId());
    constraintDisabler.restoreConstraints();
  }
  
}
