package org.atoy.atoymg;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.nio.charset.StandardCharsets;
import org.atoy.atoymg.models.Event;
import org.atoy.atoymg.services.interfaces.EventService;
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
class EventApplicationTests {

  private MockMvc mockMvc;
  private EventService service;
  private Event mockData=new Event();
  private final JdbcTemplate jdbcTemplate;
  private ConstraintDisabler constraintDisabler;

  @Autowired
  public EventApplicationTests(MockMvc mockMvc, EventService service, JdbcTemplate jdbcTemplate) throws Exception {
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
    constraintDisabler.disableConstraintsForTable("events");
    mockData = new Event();
    mockData.setTitle("mock");
    mockData.setEventdate(java.time.LocalDate.now());
    mockData.setEnddate(java.time.LocalDate.now());
    mockData.setVenuecity("mock");
    mockData.setGuestcount(0);
    mockData.setTotalbudget(0.);
    mockData.setNotes("mock");
    mockData.setCreatedat(java.time.OffsetDateTime.now());
    mockData.setUpdatedat(java.time.OffsetDateTime.now());
    
    service.createEvent(mockData);
  }
  @AfterEach
  void removingMockdatas(TestInfo testInfo){
    if(testInfo.getTags().contains("skipAfter")){
      return;
    }
    service.deleteEvent(mockData.getId());
    constraintDisabler.restoreConstraints();
  }
  

  @Test
  void testSearchEvent() throws Exception{
    ObjectMapper mapper=new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter writer=mapper.writer().withDefaultPrettyPrinter();
    String requestJson=writer.writeValueAsString(mockData);
    MockHttpServletResponse resp=mockMvc.perform(post("/events/search").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

  @Test
  void testGetAllEvent() throws Exception{
    MockHttpServletResponse resp=mockMvc.perform(get("/events")).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

   
  @Test
  void testCreateEvent() throws Exception{
    Event otherMockData=new Event();
    otherMockData.setTitle("mock");
    otherMockData.setEventdate(java.time.LocalDate.now());
    otherMockData.setEnddate(java.time.LocalDate.now());
    otherMockData.setVenuecity("mock");
    otherMockData.setGuestcount(0);
    otherMockData.setTotalbudget(0.);
    otherMockData.setNotes("mock");
    otherMockData.setCreatedat(java.time.OffsetDateTime.now());
    otherMockData.setUpdatedat(java.time.OffsetDateTime.now());
    
    service.createEvent(otherMockData);
    MockHttpServletResponse resp=mockMvc.perform(get("/events")).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
    service.deleteEvent(otherMockData.getId());
  }
  

   
  @Test
  void testModifyEvent() throws Exception{
    ObjectMapper mapper=new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter writer=mapper.writer().withDefaultPrettyPrinter();
    String requestJson=writer.writeValueAsString(mockData);
    String requestUrl="/events/%s";
    requestUrl=String.format(requestUrl, mockData.getId());
    MockHttpServletResponse resp=mockMvc.perform(put(requestUrl).contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

  @Test
  @Tag("skipAfter")
  void testDeleteEvent() throws Exception{
    service.deleteEvent(mockData.getId());
    constraintDisabler.restoreConstraints();
  }
  
}
