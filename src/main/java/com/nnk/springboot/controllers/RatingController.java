package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.RatingService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import javax.validation.Valid;

/**
 * Controller for Rating.
 * 
 * @author Antoine Lanselle
 */
@Controller
@RequestMapping(path = "/rating")
public class RatingController {

	private static final Logger LOGGER = LogManager.getLogger(RatingController.class);

	@Autowired
	private RatingService ratingService;

	/**
	 * Return the template list of all Rating in DataBase.
	 * 
	 * @param Model for adding attributes.
	 * @return String the template path.
	 */
	@GetMapping(path = "/list")
	public String home(Model model) {
		String info = "GET - Rating List page.";
		LOGGER.info(info);

		model.addAttribute("ratings", ratingService.findAllRatings());
		return "rating/list";
	}

	/**
	 * Show the form to add a Rating.
	 * 
	 * @param Rating the new Rating to be completed by the user.
	 * @return String of the template path.
	 */
	@GetMapping(path = "/add")
	public String addRatingForm(Rating rating) {
		String info = "GET - Creat Rating form.";
		LOGGER.info(info);

		return "rating/add";
	}

	/**
	 * Add Rating in parameter in DataBase, and redirect to list.
	 * 
	 * @param Rating        the Rating you want to add in DataBase.
	 * @param BindingResult verification if Rating in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 */
	@PostMapping(path = "/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		String info = "POST - Add new Rating.";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			ratingService.addRating(rating);
			model.addAttribute("ratings", ratingService.findAllRatings());
			return "redirect:/rating/list";
		} else {
			info = info + " Fail: Rating data is not valid.";
			LOGGER.info(info);

			return "rating/add";
		}
	}

	/**
	 * Show update form for Rating with id in parameter.
	 * 
	 * @param Integer the id of the Rating you want to update.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping(path = "/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Update form for Rating " + id + ".";
		LOGGER.info(info);

		Rating rating = ratingService.findById(id);
		model.addAttribute("rating", rating);
		return "rating/update";
	}

	/**
	 * Update Rating with id in parameter in DataBase with Rating in parameter, and
	 * redirect to list.
	 * 
	 * @param Integer       the id of the Rating you want to update.
	 * @param Rating        the new Rating data to update.
	 * @param BindingResult verification if Rating in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PostMapping(path = "/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model)
			throws RessourceNotFoundException {
		String info = "POST - Update Rating " + id + ".";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			rating.setId(id);
			ratingService.updateRating(rating);

			model.addAttribute("ratings", ratingService.findAllRatings());
			return "redirect:/rating/list";
		} else {
			info = info + " Fail: Rating id is not valid.";
			LOGGER.info(info);

			return "rating/update";
		}
	}

	/**
	 * Delete Rating with id in parameter from DataBase, and redirect to list.
	 * 
	 * @param Integer the id of the Rating you want to delete.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping(path = "/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Delete Rating " + id + ".";
		LOGGER.info(info);

		Rating rating = ratingService.findById(id);
		ratingService.deleteRating(rating);

		model.addAttribute("ratings", ratingService.findAllRatings());
		return "redirect:/rating/list";
	}

	/**
	 * API request - Return all Ratings in DataBase.
	 * 
	 * @return ResponseEntity<List<Rating> a response with https status OK and a
	 *         Rating List of all Ratings in DataBase as body.
	 */
	@GetMapping(path = "/all")
	public ResponseEntity<List<Rating>> getRatings() {
		String info = "API GET - Rating List of all Ratings.";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(ratingService.findAllRatings());
	}

	/**
	 * API request - Return the Rating with id in parameter.
	 * 
	 * @param Integer the id of the Rating you want to get.
	 * @return ResponseEntity<BidList> a response with https status OK and a Rating
	 *         as body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping()
	public ResponseEntity<Rating> getRating(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API GET - Rating with id: " + id + ".";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(ratingService.findById(id));
	}

	/**
	 * API request - Add the Rating in parameter in DataBase and return https status
	 * OK with message.
	 * 
	 * @param Rating the Rating data to add in DataBase.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 */
	@PostMapping()
	public ResponseEntity<String> addRating(@RequestBody Rating rating) {
		String info = "API POST - Add Rating.";
		LOGGER.info(info);

		ratingService.addRating(rating);
		String msg = "Rating has been added in DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	/**
	 * API request - Update the Rating with id in parameter in DataBase with Rating
	 * data in parameter and return https status OK with message.
	 * 
	 * @param Rating  the Rating data to put in DataBase.
	 * @param Integer the id of the Rating you want to update.
	 * 
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PutMapping()
	public ResponseEntity<String> updateRating(@RequestBody Rating rating,
			@RequestParam(name = "id", required = true) Integer id) throws RessourceNotFoundException {
		String info = "API UPDATE - Update Rating.";
		LOGGER.info(info);

		ratingService.updateRating(rating);
		String msg = "Rating has been updated in DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	/**
	 * API request - Delete Rating with id in parameter from DataBase.
	 * 
	 * @param Integer the id of the Rating you want to delete.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 * 
	 */
	@DeleteMapping()
	public ResponseEntity<String> deleteRating(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API DELETE - Rating with id: " + id + ".";
		LOGGER.info(info);

		ratingService.deleteRating(ratingService.findById(id));
		String msg = "Rating with id: " + id + " has been delete from DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}
}
