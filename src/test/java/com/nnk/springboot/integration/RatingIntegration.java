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
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class RatingIntegration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RatingRepository ratingRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		ratingRepository.deleteAll();

		Rating rating = new Rating();
		rating.setMoodysRating("test");
		rating.setSandPRating("test");
		rating.setFitchRating("test");
		rating.setOrderNumber(1);
		ratingRepository.save(rating);
	}

	@Test
	public void validateRating_shouldAddRatingInDatabase() throws Exception {
		// GIVEN
		List<Rating> listBefore = ratingRepository.findAll();

		// WHEN
		mockMvc.perform(post("/rating/validate").param("moodysRating", "newTest").param("sandPRating", "newTest")
				.param("fitchRating", "newTest").param("orderNumber", "2")).andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, ratingRepository.findAll().size());
		assertEquals("newTest", ratingRepository.findAll().get(1).getMoodysRating());
	}

	@Test
	public void updateRating_shouldUpdateRatingInDatabase() throws Exception {
		// GIVEN
		List<Rating> listBefore = ratingRepository.findAll();

		// WHEN
		mockMvc.perform(post("/rating/update/{id}", listBefore.get(0).getId()).param("moodysRating", "newTest")
				.param("sandPRating", "newTest").param("fitchRating", "newTest").param("orderNumber", "1"))
				.andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", ratingRepository.findById(listBefore.get(0).getId()).get().getMoodysRating());
		assertEquals(1, ratingRepository.findAll().size());
	}

	@Test
	public void deleteRating_shouldDeleteRatingInDatabase() throws Exception {
		// GIVEN
		List<Rating> listBefore = ratingRepository.findAll();

		// WHEN
		mockMvc.perform(get("/rating/delete/{id}", listBefore.get(0).getId())).andExpect(status().is3xxRedirection());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, ratingRepository.findAll().size());
	}

	@Test
	public void getRating_shouldReturnListOfRating() throws Exception {
		// GIVEN
		List<Rating> listBefore = ratingRepository.findAll();

		// WHEN
		mockMvc.perform(get("/rating/all"));

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(1, ratingRepository.findAll().size());
	}

	@Test
	public void addRating_shouldAddRatingInRepository() throws Exception {
		// GIVEN
		List<Rating> listBefore = ratingRepository.findAll();

		Rating rating = new Rating();
		rating.setMoodysRating("newTest");
		rating.setSandPRating("newTest");
		rating.setFitchRating("newTest");
		rating.setOrderNumber(2);

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = post("/rating");
		request.content(objectMapper.writeValueAsString(rating));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, ratingRepository.findAll().size());
		assertEquals("newTest", ratingRepository.findAll().get(1).getMoodysRating());
	}

	@Test
	public void updateRating_shouldUpdateTheRatingInDatabase() throws Exception {
		// GIVEN
		List<Rating> listBefore = ratingRepository.findAll();

		Rating rating = new Rating();
		rating.setId(listBefore.get(0).getId());
		rating.setMoodysRating("newTest");
		rating.setSandPRating("newTest");
		rating.setFitchRating("newTest");
		rating.setOrderNumber(2);

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = put("/rating");
		request.content(objectMapper.writeValueAsString(rating));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", ratingRepository.findAll().get(0).getMoodysRating());
		assertEquals(1, ratingRepository.findAll().size());
	}

	@Test
	public void deleteRating_shouldDeleteTheRatingInDatabase() throws Exception {
		// GIVEN
		List<Rating> listBefore = ratingRepository.findAll();

		// WHEN
		mockMvc.perform(delete("/rating").param("id", listBefore.get(0).getId().toString())).andExpect(status().isOk());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, ratingRepository.findAll().size());
	}

}
