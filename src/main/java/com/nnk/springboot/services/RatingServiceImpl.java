package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;

/**
 * Service implementation for Rating.
 * 
 * @author Antoine Lanselle
 */
@Service
public class RatingServiceImpl implements RatingService {

	private static final Logger LOGGER = LogManager.getLogger(RatingServiceImpl.class);

	@Autowired
	private RatingRepository ratingRepository;

	/**
	 * Return all Ratings in DataBase.
	 *
	 * @return List<Rating> all the Ratings in the DataBase.
	 */
	@Override
	public List<Rating> findAllRatings() {
		String info = "Returning all Rating from DataBase.";
		LOGGER.info(info);

		return ratingRepository.findAll();
	}

	/**
	 * Return specific Rating from DataBase with id in parameter.
	 *
	 * @param Integer the id of the Rating.
	 * @return Rating with id in parameter in DataBase.
	 * @throw RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@Override
	public Rating findById(Integer id) throws RessourceNotFoundException {
		String info = "Looking for Rating with id " + id + " in DataBase.";
		String error = "Fail: Rating with id " + id + " not found in DataBase.";
		LOGGER.info(info);

		return ratingRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException(error));
	}

	/**
	 * Add Rating in parameter in DataBase.
	 *
	 * @param Rating you want to add in DataBase.
	 * @return Rating the added Rating.
	 */
	@Override
	public Rating addRating(Rating rating) {
		String info = "Adding Rating in DataBase.";
		LOGGER.info(info);

		return ratingRepository.save(rating);
	}

	/**
	 * Update Rating with Rating id in parameter in DataBase with Rating in
	 * parameter.
	 *
	 * @param Rating the new Rating data we want to update.
	 * @return Rating the updated Rating.
	 * @throw RessourceNotFoundException when Rating id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public Rating updateRating(Rating rating) throws RessourceNotFoundException {
		String info = "Updating Rating in DataBase.";
		LOGGER.info(info);

		if (ratingRepository.existsById(rating.getId())) {
			return ratingRepository.save(rating);
		} else {
			String error = "Fail: Rating with id " + rating.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

	/**
	 * Delete Rating with Rating id in parameter from DataBase.
	 *
	 * @param Rating the Rating you want to delete from DataBase.
	 * @throw RessourceNotFoundException when Rating id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public void deleteRating(Rating rating) throws RessourceNotFoundException {
		String info = "Deleting Rating in DataBase.";
		LOGGER.info(info);

		if (ratingRepository.existsById(rating.getId())) {
			ratingRepository.delete(rating);
		} else {
			String error = "Fail: Rating with id " + rating.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

}
