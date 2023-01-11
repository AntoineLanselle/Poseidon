package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.RatingService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Controller for Rating.
 * 
 * @author Antoine Lanselle
 */
@Controller
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
	@RequestMapping("/rating/list")
	public String home(Model model) {
		String info = "REQUEST - Rating List page.";
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
	@GetMapping("/rating/add")
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
	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		String info = "CREAT - Add new Rating.";

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
	@GetMapping("/rating/update/{id}")
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
	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model)
			throws RessourceNotFoundException {
		String info = "UPDATE - Update Rating " + id + ".";

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
	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "DELETE - Delete Rating " + id + ".";
		LOGGER.info(info);

		Rating rating = ratingService.findById(id);
		ratingService.deleteRating(rating);

		model.addAttribute("ratings", ratingService.findAllRatings());
		return "redirect:/rating/list";
	}
}
