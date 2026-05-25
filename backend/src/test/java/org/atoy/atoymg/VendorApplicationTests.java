package org.atoy.atoymg;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.nio.charset.StandardCharsets;
import org.atoy.atoymg.models.Vendor;
import org.atoy.atoymg.services.interfaces.VendorService;
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
class VendorApplicationTests {

  private MockMvc mockMvc;
  private VendorService service;
  private Vendor mockData=new Vendor();
  private final JdbcTemplate jdbcTemplate;
  private ConstraintDisabler constraintDisabler;

  @Autowired
  public VendorApplicationTests(MockMvc mockMvc, VendorService service, JdbcTemplate jdbcTemplate) throws Exception {
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
    constraintDisabler.disableConstraintsForTable("vendors");
    mockData = new Vendor();
    mockData.setBusinessname("mock");
    mockData.setContactemail("mock");
    mockData.setContactphone("mock");
    mockData.setWebsite("mock");
    mockData.setCity("mock");
    mockData.setCountrycode("mock");
    mockData.setBaseprice(0.);
    mockData.setRating(0.);
    mockData.setRatingcount(0);
    mockData.setDescription("mock");
    mockData.setIsverified(true);
    mockData.setIsactive(true);
    mockData.setCreatedat(java.time.OffsetDateTime.now());
    mockData.setUpdatedat(java.time.OffsetDateTime.now());
    
    service.createVendor(mockData);
  }
  @AfterEach
  void removingMockdatas(TestInfo testInfo){
    if(testInfo.getTags().contains("skipAfter")){
      return;
    }
    service.deleteVendor(mockData.getId());
    constraintDisabler.restoreConstraints();
  }
  

  @Test
  void testSearchVendor() throws Exception{
    ObjectMapper mapper=new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter writer=mapper.writer().withDefaultPrettyPrinter();
    String requestJson=writer.writeValueAsString(mockData);
    MockHttpServletResponse resp=mockMvc.perform(post("/vendors/search").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

  @Test
  void testGetAllVendor() throws Exception{
    MockHttpServletResponse resp=mockMvc.perform(get("/vendors")).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

   
  @Test
  void testCreateVendor() throws Exception{
    Vendor otherMockData=new Vendor();
    otherMockData.setBusinessname("mock");
    otherMockData.setContactemail("mock");
    otherMockData.setContactphone("mock");
    otherMockData.setWebsite("mock");
    otherMockData.setCity("mock");
    otherMockData.setCountrycode("mock");
    otherMockData.setBaseprice(0.);
    otherMockData.setRating(0.);
    otherMockData.setRatingcount(0);
    otherMockData.setDescription("mock");
    otherMockData.setIsverified(true);
    otherMockData.setIsactive(true);
    otherMockData.setCreatedat(java.time.OffsetDateTime.now());
    otherMockData.setUpdatedat(java.time.OffsetDateTime.now());
    
    service.createVendor(otherMockData);
    MockHttpServletResponse resp=mockMvc.perform(get("/vendors")).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
    service.deleteVendor(otherMockData.getId());
  }
  

   
  @Test
  void testModifyVendor() throws Exception{
    ObjectMapper mapper=new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter writer=mapper.writer().withDefaultPrettyPrinter();
    String requestJson=writer.writeValueAsString(mockData);
    String requestUrl="/vendors/%s";
    requestUrl=String.format(requestUrl, mockData.getId());
    MockHttpServletResponse resp=mockMvc.perform(put(requestUrl).contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

  @Test
  @Tag("skipAfter")
  void testDeleteVendor() throws Exception{
    service.deleteVendor(mockData.getId());
    constraintDisabler.restoreConstraints();
  }
  
}
