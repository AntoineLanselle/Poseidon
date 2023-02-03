package com.nnk.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

	@Mock
	private RatingService ratingService;
	@Mock
	private UserService userService;
	@Mock
	private Model model;
	@InjectMocks
	private RatingController ratingController;

	@Test
	public void showRatings_ShouldReturnStringOfTemplatePath() {
		// GIVEN
		User user = new User();
		user.setRole("USER");
		when(userService.getCurrentUser()).thenReturn(user);
		when(ratingService.findAllRatings()).thenReturn(new ArrayList<Rating>());

		// WHEN
		String testResult = ratingController.showRatings(model);

		// THEN
		assertEquals("rating/list", testResult);
	}

	@Test
	public void addRatingForm_ShouldReturnStringOfTemplatePath() {
		// GIVEN

		// WHEN
		String testResult = ratingController.addRatingForm(new Rating());

		// THEN
		assertEquals("rating/add", testResult);
	}

	@Test
	public void validate_ShouldRedirectToRating() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = ratingController.validateRating(new Rating(), result, model);

		// THEN
		assertEquals("redirect:/rating/list", testResult);
	}

	@Test
	public void validate_ShouldStringOfTemplatePathRatingAdd() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = ratingController.validateRating(new Rating(), result, model);

		// THEN
		assertEquals("rating/add", testResult);
	}

	@Test
	public void showUpdateRatingForm_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		when(ratingService.findById(1)).thenReturn(new Rating());

		// WHEN
		String testResult = ratingController.showUpdateRatingForm(1, model);

		// THEN
		assertEquals("rating/update", testResult);
	}

	@Test
	public void updateRating_ShouldRedirectToCurvePoint() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = ratingController.updateRating(1, new Rating(), result, model);

		// THEN
		assertEquals("redirect:/rating/list", testResult);
	}

	@Test
	public void updateRating_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = ratingController.updateRating(1, new Rating(), result, model);

		// THEN
		assertEquals("rating/update", testResult);
	}

	@Test
	public void deleteRating_ShouldRedirectToRating() throws RessourceNotFoundException {
		// GIVEN
		when(ratingService.findById(1)).thenReturn(new Rating());
		when(ratingService.findAllRatings()).thenReturn(new ArrayList<Rating>());

		// WHEN
		String testResult = ratingController.deleteRating(1, model);

		// THEN
		assertEquals("redirect:/rating/list", testResult);
	}

	@Test
	public void getCRating_ShouldReturnResponseEntityWithStatusOKAndListOfRatingsAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		Rating rating = new Rating();
		List<Rating> ratings = new ArrayList<Rating>();
		ratings.add(rating);
		when(ratingService.findAllRatings()).thenReturn(ratings);

		// WHEN
		ResponseEntity<List<Rating>> testResult = ratingController.getRatings();

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(1, testResult.getBody().size());
		assertTrue(testResult.getBody().contains(rating));
	}

	@Test
	public void getRating_ShouldReturnResponseEntityWithStatusOKAndRatingAsBody() throws RessourceNotFoundException {
		// GIVEN
		Rating rating = new Rating();
		when(ratingService.findById(1)).thenReturn(rating);

		// WHEN
		ResponseEntity<Rating> testResult = ratingController.getRating(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(rating, testResult.getBody());
	}

	@Test
	public void addRating_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		Rating rating = new Rating();

		// WHEN
		ResponseEntity<String> testResult = ratingController.addRating(rating);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("Rating has been added in DataBase.", testResult.getBody());
	}

	@Test
	public void updateRating_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		Rating rating = new Rating();

		// WHEN
		ResponseEntity<String> testResult = ratingController.updateRating(rating);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("Rating has been updated in DataBase.", testResult.getBody());
	}

	@Test
	public void deleteRating_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN

		// WHEN
		ResponseEntity<String> testResult = ratingController.deleteRating(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("Rating with id: 1 has been delete from DataBase.", testResult.getBody());
	}

}
