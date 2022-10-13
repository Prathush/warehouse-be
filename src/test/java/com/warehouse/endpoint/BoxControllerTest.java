package com.warehouse.endpoint;

import static org.mockito.Mockito.when;
import java.nio.charset.StandardCharsets;

import com.warehouse.service.api.BoxService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BoxController.class)
public class BoxControllerTest
{
	@MockBean
    BoxService _boxService;
	HttpHeaders httpHeaders;
	@Autowired
	private MockMvc mockMvc;

	@Before
	public void init()
	{
		httpHeaders = new HttpHeaders()
		{{
			String auth = "Demo" + ":" + "Demo123";
			byte[] encodedAuth = Base64.encodeBase64(
				auth.getBytes(StandardCharsets.US_ASCII));
			String authHeader = "Basic " + new String(encodedAuth);
			set("Authorization", authHeader);
		}};
	}

	@Test
	public void shouldCreateBox() throws Exception
	{
		String boxDetails = "{\n"
			+ "\t\"boxName\": \"Rama\",\n"
			+ "\t\"boxCapacity\": 1\n"
			+ "}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/warehouse/v1/box/createBox")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.headers(httpHeaders)
			.content(boxDetails);

		MvcResult mockResponse = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertNotNull(mockResponse);
		Assert.assertEquals(201, mockResponse.getResponse().getStatus());
	}

	@Test
	public void shouldThrowExceptionWhenMandatoryFieldsMissing() throws Exception
	{
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/warehouse/v1/box/createBox")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.headers(httpHeaders)
			.content("");

		MvcResult mockResponse = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertNotNull(mockResponse);
		Assert.assertEquals(500, mockResponse.getResponse().getStatus());
	}

	@Test
	public void shouldGetAvailableBoxList() throws Exception
	{
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/warehouse/v1/box/getAvailableBox")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.headers(httpHeaders);

		MvcResult mockResponse = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertNotNull(mockResponse);
		Assert.assertEquals(200, mockResponse.getResponse().getStatus());
	}

	@Test
	public void shouldThrowBoxNotAvailableWithAvailableCapacity() throws Exception
	{
		when(_boxService.getAvailableBoxList()).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/warehouse/v1/box/getAvailableBox")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.headers(httpHeaders);

		MvcResult mockResponse = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertNotNull(mockResponse);
		Assert.assertEquals(500, mockResponse.getResponse().getStatus());
	}
}
