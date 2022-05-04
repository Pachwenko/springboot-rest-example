package com.example.restservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach()
	private void setUp() {
		// reset counter somehow? or just ignore it cause we should use a real database anyways
		// counter.set(0);
	}

	private String getGreetingUrl() {
		return "http://localhost:" + port + "/greeting";
	}

	private Map<String, String> convertJSONToMap(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json,
					new TypeReference<HashMap<String, String>>() {
					});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String callAPI(String url) {
		return this.restTemplate.getForObject(url, String.class);
	}

	@Test
	public void testGetGreetingWithCustomName() {
		Map<String, String> response = convertJSONToMap(callAPI(getGreetingUrl() + "?name=Mr+Prefect"));
		assertThat(response.get("content")).isEqualTo("Hello, Mr Prefect!");
	}

	@Test
	public void testGetGreetingMultiple() {
		Map<String, String> response = convertJSONToMap(callAPI(getGreetingUrl()));
		assertThat(response.get("id")).isEqualTo("1");
		assertThat(response.get("content")).isEqualTo("Hello, World!");
		response = convertJSONToMap(callAPI(getGreetingUrl()));
		assertThat(response.get("id")).isEqualTo("2");
		assertThat(response.get("content")).isEqualTo("Hello, World!");
	}
}
