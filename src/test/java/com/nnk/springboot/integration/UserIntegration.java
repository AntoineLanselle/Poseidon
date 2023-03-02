package com.nnk.springboot.integration;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class UserIntegration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		userRepository.deleteAll();

		User user = new User();
		user.setUsername("test");
		user.setPassword("testPass%1");
		user.setFullname("test");
		user.setRole("USER");
		userRepository.save(user);
	}

	@Test
	public void validateUser_shouldAddUserInDatabase() throws Exception {
		// GIVEN
		List<User> listBefore = userRepository.findAll();

		// WHEN
		mockMvc.perform(post("/user/validate").param("username", "newTest").param("password", "newTestPass%1")
				.param("fullname", "newTest").param("role", "USER")).andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, userRepository.findAll().size());
		assertEquals("newTest", userRepository.findAll().get(1).getUsername());
	}

	@Test
	public void updateUser_shouldUpdateUserInDatabase() throws Exception {
		// GIVEN
		List<User> listBefore = userRepository.findAll();

		// WHEN
		mockMvc.perform(post("/user/update/{id}", listBefore.get(0).getId()).param("username", "newTest")
				.param("password", "newTestPass%1").param("fullname", "newTest").param("role", "USER"))
				.andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", userRepository.findById(listBefore.get(0).getId()).get().getUsername());
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	public void deleteUser_shouldDeleteUserInDatabase() throws Exception {
		// GIVEN
		List<User> listBefore = userRepository.findAll();

		// WHEN
		mockMvc.perform(get("/user/delete/{id}", listBefore.get(0).getId())).andExpect(status().is3xxRedirection());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, userRepository.findAll().size());
	}

	@Test
	public void getUser_shouldReturnListOfUser() throws Exception {
		// GIVEN
		List<User> listBefore = userRepository.findAll();

		// WHEN
		mockMvc.perform(get("/user/all"));

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	public void addUser_shouldAddUserInRepository() throws Exception {
		// GIVEN
		List<User> listBefore = userRepository.findAll();

		User user = new User();
		user.setUsername("newTest");
		user.setPassword("newTestPass%1");
		user.setFullname("newTest");
		user.setRole("USER");

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = post("/user");
		request.content(objectMapper.writeValueAsString(user));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, userRepository.findAll().size());
		assertEquals("newTest", userRepository.findAll().get(1).getUsername());
	}

	@Test
	public void updateUser_shouldUpdateTheUserInDatabase() throws Exception {
		// GIVEN
		List<User> listBefore = userRepository.findAll();
		
		User user = new User();
		user.setId(listBefore.get(0).getId());
		user.setUsername("newTest");
		user.setPassword("newTestPass%1");
		user.setFullname("newTest");
		user.setRole("USER");

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = put("/user");
		request.content(objectMapper.writeValueAsString(user));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", userRepository.findAll().get(0).getUsername());
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	public void deleteUser_shouldDeleteTheUserInDatabase() throws Exception {
		// GIVEN
		List<User> listBefore = userRepository.findAll();

		// WHEN
		mockMvc.perform(delete("/user").param("id", listBefore.get(0).getId().toString())).andExpect(status().isOk());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, userRepository.findAll().size());
	}

}
