package org.atoy.atoymg;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.nio.charset.StandardCharsets;
import org.atoy.atoymg.models.Vendorcategorie;
import org.atoy.atoymg.services.interfaces.VendorcategorieService;
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
class VendorcategorieApplicationTests {

  private MockMvc mockMvc;
  private VendorcategorieService service;
  private Vendorcategorie mockData=new Vendorcategorie();
  private final JdbcTemplate jdbcTemplate;
  private ConstraintDisabler constraintDisabler;

  @Autowired
  public VendorcategorieApplicationTests(MockMvc mockMvc, VendorcategorieService service, JdbcTemplate jdbcTemplate) throws Exception {
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
    constraintDisabler.disableConstraintsForTable("vendorcategories");
    mockData = new Vendorcategorie();
    mockData.setCode("mock");
    mockData.setLabel("mock");
    
    service.createVendorcategorie(mockData);
  }
  @AfterEach
  void removingMockdatas(TestInfo testInfo){
    if(testInfo.getTags().contains("skipAfter")){
      return;
    }
    service.deleteVendorcategorie(mockData.getId());
    constraintDisabler.restoreConstraints();
  }
  

  @Test
  void testSearchVendorcategorie() throws Exception{
    ObjectMapper mapper=new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter writer=mapper.writer().withDefaultPrettyPrinter();
    String requestJson=writer.writeValueAsString(mockData);
    MockHttpServletResponse resp=mockMvc.perform(post("/vendorcategories/search").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

  @Test
  void testGetAllVendorcategorie() throws Exception{
    MockHttpServletResponse resp=mockMvc.perform(get("/vendorcategories")).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

   
  @Test
  void testCreateVendorcategorie() throws Exception{
    Vendorcategorie otherMockData=new Vendorcategorie();
    otherMockData.setCode("mock");
    otherMockData.setLabel("mock");
    
    service.createVendorcategorie(otherMockData);
    MockHttpServletResponse resp=mockMvc.perform(get("/vendorcategories")).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
    service.deleteVendorcategorie(otherMockData.getId());
  }
  

   
  @Test
  void testModifyVendorcategorie() throws Exception{
    ObjectMapper mapper=new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter writer=mapper.writer().withDefaultPrettyPrinter();
    String requestJson=writer.writeValueAsString(mockData);
    String requestUrl="/vendorcategories/%s";
    requestUrl=String.format(requestUrl, mockData.getId());
    MockHttpServletResponse resp=mockMvc.perform(put(requestUrl).contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn().getResponse();
    System.out.println(resp.getContentAsString());
  }

  @Test
  @Tag("skipAfter")
  void testDeleteVendorcategorie() throws Exception{
    service.deleteVendorcategorie(mockData.getId());
    constraintDisabler.restoreConstraints();
  }
  
}
