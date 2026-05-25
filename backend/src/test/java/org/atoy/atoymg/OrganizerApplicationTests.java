package org.atoy.atoymg;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.nio.charset.StandardCharsets;
import org.atoy.atoymg.models.Organizer;
import org.atoy.atoymg.services.interfaces.OrganizerService;
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
class OrganizerApplicationTests {

  private MockMvc mockMvc;
  private OrganizerService service;
  private Organizer mockData=new Organizer();
  private final JdbcTemplate jdbcTemplate;
  private ConstraintDisabler constraintDisabler;

  @Autowired
  public OrganizerApplicationTests(MockMvc mockMvc, OrganizerService service, JdbcTemplate jdbcTemplate) throws Exception {
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
    constraintDisabler.disableConstraintsForTable("organizers");
    mockData = new Organizer();
    mockData.setAgencyname("mock");
    mockData.setSlug("mock");
    mockData.setEmail("mock");
    mockData.setPhone("mock");
    mockData.setCity("mock");
    mockData.setIsactive(true);
    mockData.setCreatedat(java.time.OffsetDateTime.now());
    mockData.setUpdatedat(java.time.OffsetDateTime.now());
    
    service.createOrganizer(mockData);
  }
  @AfterEach
  void removingMockdatas(TestInfo testInfo){
    if(testInfo.getTags().contains("skipAfter")){
      return;
    }
    service.deleteOrganizer(mockData.getId());
    constraintDisabler.restoreConstraints();
  }
  

  @Test
  void testSearchOrganizer() throws Exception{
    ObjectMapper mapper=new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter writer=mapper.writer().withDefaultPrettyPrinter();
    String requestJson=writer.writeValueAsString(mockData);
    MockHttpServletResponse resp=mockMvc.perform(post("/organizers/search").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

  @Test
  void testGetAllOrganizer() throws Exception{
    MockHttpServletResponse resp=mockMvc.perform(get("/organizers")).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

   
  @Test
  void testCreateOrganizer() throws Exception{
    Organizer otherMockData=new Organizer();
    otherMockData.setAgencyname("mock");
    otherMockData.setSlug("mock");
    otherMockData.setEmail("mock");
    otherMockData.setPhone("mock");
    otherMockData.setCity("mock");
    otherMockData.setIsactive(true);
    otherMockData.setCreatedat(java.time.OffsetDateTime.now());
    otherMockData.setUpdatedat(java.time.OffsetDateTime.now());
    
    service.createOrganizer(otherMockData);
    MockHttpServletResponse resp=mockMvc.perform(get("/organizers")).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
    service.deleteOrganizer(otherMockData.getId());
  }
  

   
  @Test
  void testModifyOrganizer() throws Exception{
    ObjectMapper mapper=new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter writer=mapper.writer().withDefaultPrettyPrinter();
    String requestJson=writer.writeValueAsString(mockData);
    String requestUrl="/organizers/%s";
    requestUrl=String.format(requestUrl, mockData.getId());
    MockHttpServletResponse resp=mockMvc.perform(put(requestUrl).contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

  @Test
  @Tag("skipAfter")
  void testDeleteOrganizer() throws Exception{
    service.deleteOrganizer(mockData.getId());
    constraintDisabler.restoreConstraints();
  }
  
}
