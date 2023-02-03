package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class RatingServiceImplTest {

	@Mock
	private RatingRepository ratingRepository;
	@InjectMocks
	private RatingServiceImpl ratingService;
	private static List<Rating> ratingRepo;
	
	@BeforeAll
	public static void init() {
		Rating ratingOne = new Rating();
		Rating ratingTwo = new Rating();
		ratingRepo = new ArrayList<Rating>();
		ratingRepo.add(ratingOne);
		ratingRepo.add(ratingTwo);
	}
	
	@Test
	public void findAllRatings_ShouldReturnListOfAllRatings() {
		// GIVEN
		when(ratingRepository.findAll()).thenReturn(ratingRepo);
		
		// WHEN
		List<Rating> testResult = ratingService.findAllRatings();
		
		// THEN
		assertEquals(ratingRepo, testResult);
	}
	
	@Test
	public void findById_ShouldReturnSpecificRating() throws RessourceNotFoundException {
		// GIVEN
		Optional<Rating> optRating = Optional.of(ratingRepo.get(0));
		when(ratingRepository.findById(1)).thenReturn(optRating);
		
		// WHEN
		Rating testResult = ratingService.findById(1);
		
		// THEN
		assertEquals(ratingRepo.get(0), testResult);
	}
	
	@Test
	public void findById_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		Optional<Rating> ratingNull = Optional.empty();
		when(ratingRepository.findById(1)).thenReturn(ratingNull);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			ratingService.findById(1);
		});
	}
	
	@Test
	public void addRating_ShouldReturnRatingInParameter() throws RessourceNotFoundException {
		// GIVEN
		when(ratingRepository.save(ratingRepo.get(0))).thenReturn(ratingRepo.get(0));
		
		// WHEN 
		Rating testResult = ratingService.addRating(ratingRepo.get(0));
		
		// THEN
		assertEquals(ratingRepo.get(0), testResult);
	}
	
	@Test
	public void updateRating_ShouldReturnRatingInParameter() throws RessourceNotFoundException {
		// GIVEN
		Rating rating = new Rating(); 
		when(ratingRepository.existsById(rating.getId())).thenReturn(true);
		when(ratingRepository.save(rating)).thenReturn(rating);
		
		// WHEN 
		Rating testResult = ratingService.updateRating(rating);
		
		// THEN
		assertEquals(rating, testResult);
	}
	
	@Test
	public void updateRating_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		Rating rating = new Rating(); 
		rating.setId(404);
		when(ratingRepository.existsById(404)).thenReturn(false);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			ratingService.updateRating(rating);
		});
	}
	
	@Test
	public void deleteRating_ShouldReturnRatingInParameter() throws RessourceNotFoundException {
		// GIVEN
		Rating rating = new Rating(); 
		when(ratingRepository.existsById(rating.getId())).thenReturn(true);
		
		// WHEN
		ratingService.deleteRating(rating);
		
		// THEN
		verify(ratingRepository).delete(rating);
	}
	
	@Test
	public void deleteRating_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		Rating rating = new Rating(); 
		rating.setId(404);
		when(ratingRepository.existsById(404)).thenReturn(false);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			ratingService.deleteRating(rating);
		});
	}
	
}
