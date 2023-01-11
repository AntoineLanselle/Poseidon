package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RessourceNotFoundException;

/**
 * Service for Rating.
 * 
 * @author Antoine Lanselle
 */
public interface RatingService {

	public List<Rating> findAllRatings();

	public Rating findById(Integer id) throws RessourceNotFoundException;

	public Rating addRating(Rating rating);

	public Rating updateRating(Rating rating) throws RessourceNotFoundException;

	public void deleteRating(Rating rating) throws RessourceNotFoundException;
	
}
