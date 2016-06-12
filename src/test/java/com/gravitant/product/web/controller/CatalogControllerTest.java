
package com.gravitant.product.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.gravitant.product.RestApiEndpoint;
import com.gravitant.product.service.dto.CatalogDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration (classes = RestApiEndpoint.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:test.properties")
@Sql({"classpath:data.sql"})
@Sql(scripts = "classpath:delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class CatalogControllerTest {

	private static final int NON_EXISTING_ID = 999;
	
	private static final String GREATER_THAN_MAX_SIZE_NAME = "This name string is longer than 50 characters";
	private static final String LESS_THAN_MIN_SIZE_NAME = "";
	
	private MediaType HAL_JSON_CONTENT_TYPE = new MediaType("application", "hal+json");
	
	private MockMvc mockMvc;
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired private WebApplicationContext wac;
	
	@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testListResources() throws Exception {
        List<CatalogDto> expectedData = loadCollectionResourceTestData("docs/sample/Catalog-collection-get-response-all.json");
		
		this.mockMvc.perform(get("/catalogs"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
			.andExpect(jsonPath("$._embedded.catalogResourceList", hasSize(3)))
;
	}

	@Test
	public void testCreateResource() throws Exception {
        CatalogDto catalogDto = loadResourceTestData("docs/sample/Catalog-collection-post-request.json");
		
		this.mockMvc.perform(post("/catalogs")
        			.contentType(HAL_JSON_CONTENT_TYPE)
        			.content(json(catalogDto)))
        		.andExpect(status().isCreated()); 
	}
	
	@Test
	public void testUpdateResource() throws Exception {
        	String IDENTIFIER = "54ba91be-6916-41b1-a004-8fe9c8526962";
        	CatalogDto catalogDto = loadResourceTestData("docs/sample/Catalog-resource-put-request.json");
		
		this.mockMvc.perform(put("/catalogs/" + IDENTIFIER)
						.contentType(HAL_JSON_CONTENT_TYPE)
						.content(json(catalogDto)))
					.andExpect(status().isNoContent()); 
	}
	
	@Test
	public void testGetResource() throws Exception {
		String IDENTIFIER = "54ba91be-6916-41b1-a004-8fe9c8526962";
		CatalogDto expectedData = loadResourceTestData("docs/sample/Catalog-resource-get-response.json");
		
		this.mockMvc.perform(get("/catalogs/" + IDENTIFIER))
			.andExpect(status().isOk())
			.andDo(print());
/*		
			.andExpect(jsonPath("created_by", is(expectedData.getCreatedBy())))
			.andExpect(jsonPath("created_at", is(expectedData.getCreatedAt())))
			.andExpect(jsonPath("last_modified_by", is(expectedData.getLastModifiedBy())))
			.andExpect(jsonPath("last_modified_at", is(expectedData.getLastModifiedAt())))
			.andExpect(jsonPath("_links.self.href", containsString("/catalogs/" + IDENTIFIER)));
*/
	
	}
	
	@Test
	public void testDeleteResource() throws Exception {
		String IDENTIFIER = "54ba91be-6916-41b1-a004-8fe9c8526962";
		
		this.mockMvc.perform(delete("/catalogs/" + IDENTIFIER))
				.andExpect(status().isNoContent()); 
	}
	
	@Test
	public void testCreateDuplicateResource() throws Exception { 
		CatalogDto duplicateCatalog = loadResourceTestData("docs/sample/Catalog-collection-post-request-duplicate.json");
        	this.mockMvc.perform(post("/catalogs")
                		.contentType(HAL_JSON_CONTENT_TYPE)
                		.content(json(duplicateCatalog)))
                	.andExpect(status().isConflict());  
	}
	
	@Test
	public void testGetResourceWithNonExistingId() throws Exception {
        CatalogDto invalidCatalog = new CatalogDto();

		this.mockMvc.perform(post("/catalogs")
						.contentType(HAL_JSON_CONTENT_TYPE)
                        .content(this.json(invalidCatalog)))
					.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testCreateResourceWithMissingRequiredFields() throws Exception {
		CatalogDto requiredFieldMissingCatalog = new CatalogDto();
		this.mockMvc.perform(post("/catalogs")
				.contentType(HAL_JSON_CONTENT_TYPE)
                             .content(this.json(requiredFieldMissingCatalog)))
			.andExpect(status().isBadRequest()); 
	}
	
	@Ignore
	@Test
	public void testCreateResourceWithLargeSizeFields() throws Exception {
		CatalogDto largeSizeCatalog = new CatalogDto();
        //largeSizeCatalog.setName(GREATER_THAN_MAX_SIZE_NAME);
			
		this.mockMvc.perform(post("/catalogs")
				.contentType(HAL_JSON_CONTENT_TYPE)
                             .content(this.json(largeSizeCatalog)))
			.andExpect(status().isBadRequest()); 
	}
	
	@Ignore
	@Test
	public void testCreateResourceWithSmallSizeFields() throws Exception {
        CatalogDto smallSizeCatalog = new CatalogDto();
       // smallSizeCatalog.setName(LESS_THAN_MIN_SIZE_NAME);
        
		this.mockMvc.perform(post("/catalogs")
				.contentType(HAL_JSON_CONTENT_TYPE)
                             .content(this.json(smallSizeCatalog)))
			.andExpect(status().isBadRequest()); 
	}

	@Ignore
	@Test
	public void testUpdateResourceWithNonExistingId() throws Exception {
        CatalogDto nonexistingCatalog = new CatalogDto();
       // nonexistingCatalog.setName("nonexisting");
			
		this.mockMvc.perform(put("/catalogs/" + NON_EXISTING_ID)
				.contentType(HAL_JSON_CONTENT_TYPE)
				.content(this.json(nonexistingCatalog)))
			.andExpect(status().isNotFound()); 
	}
	
	private List<CatalogDto> loadCollectionResourceTestData(String resourceLocation) throws Exception {
		Resource testExpectedDataResource = new FileSystemResource(resourceLocation);
        return this.jsonMapper().readValue(testExpectedDataResource.getInputStream(), this.jsonMapper().getTypeFactory().constructCollectionType(List.class, CatalogDto.class));
	}
	
	private CatalogDto loadResourceTestData(String resourceLocation) throws Exception {
		Resource testExpectedDataResource = new FileSystemResource(resourceLocation);
        return this.jsonMapper().readValue(testExpectedDataResource.getInputStream(), CatalogDto.class);
	}
	
	private ObjectMapper jsonMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		objectMapper.registerModule(new JodaModule());
		return objectMapper;
	}
	
	@SuppressWarnings("unchecked")
    protected String json(CatalogDto catalogObject) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(catalogObject, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
	
	
	
}
