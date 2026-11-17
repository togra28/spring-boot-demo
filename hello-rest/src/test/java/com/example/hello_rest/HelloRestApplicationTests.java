package com.example.hello_rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class HelloRestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void helloEndpointReturnsGreeting() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello Spring REST!"));
	}

	@Test
	void rootServesTheCrudPage() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("index.html"));
	}

	@Test
	void peopleCanBeCreatedReadUpdatedAndDeleted() throws Exception {
		MvcResult createResult = mockMvc.perform(post("/api/people")
						.contentType(APPLICATION_JSON)
						.content("""
								{"name":"Ada Lovelace","email":"ada@example.com"}
								"""))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andExpect(jsonPath("$.name").value("Ada Lovelace"))
				.andReturn();
		String personUrl = createResult.getResponse().getHeader("Location");
		assertNotNull(personUrl);

		mockMvc.perform(get(personUrl))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("ada@example.com"));

		mockMvc.perform(put(personUrl)
						.contentType(APPLICATION_JSON)
						.content("""
								{"name":"Ada Byron","email":"ada.byron@example.com"}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Ada Byron"));

		mockMvc.perform(delete(personUrl))
				.andExpect(status().isNoContent());

		mockMvc.perform(get(personUrl))
				.andExpect(status().isNotFound());
	}

	@Test
	void peopleRequireAValidNameAndEmail() throws Exception {
		mockMvc.perform(post("/api/people")
						.contentType(APPLICATION_JSON)
						.content("""
								{"name":" ","email":"not-an-email"}
								"""))
				.andExpect(status().isBadRequest());
	}

}
