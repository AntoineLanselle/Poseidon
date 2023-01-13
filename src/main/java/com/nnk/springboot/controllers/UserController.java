package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.UserService;

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
 * Controller for User.
 * 
 * @author Antoine Lanselle
 */
@Controller
public class UserController {

	private static final Logger LOGGER = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * Return the template list of all Users in DataBase.
	 * 
	 * @param Model for adding attributes.
	 * @return String the template path.
	 */
	@RequestMapping("/user/list")
	public String home(Model model) {
		String info = "REQUEST - User List page.";
		LOGGER.info(info);

		model.addAttribute("users", userService.findAllUsers());
		return "user/list";
	}

	/**
	 * Show the form to add a User.
	 * 
	 * @param User the new User to be completed by the user.
	 * @return String of the template path.
	 */
	@GetMapping("/user/add")
	public String addUser(User user) {
		String info = "GET - Creat User form.";
		LOGGER.info(info);

		return "user/add";
	}

	/**
	 * Add User in parameter in DataBase, and redirect to list.
	 * 
	 * @param User          the User you want to add in DataBase.
	 * @param BindingResult verification if User in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 */
	@PostMapping("/user/validate")
	public String validate(@Valid User user, BindingResult result, Model model) {
		String info = "CREAT - Add new User.";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			userService.addUser(user);
			model.addAttribute("users", userService.findAllUsers());
			return "redirect:/user/list";
		} else {
			info = info + " Fail: User data is not valid.";
			LOGGER.info(info);

			return "user/add";
		}
	}

	/**
	 * Show update form for User with id in parameter.
	 * 
	 * @param Integer the id of the User you want to update.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Update form for User " + id + ".";
		LOGGER.info(info);

		User user = userService.findById(id);
		user.setPassword("");
		model.addAttribute("user", user);
		return "user/update";
	}

	/**
	 * Update User with id in parameter in DataBase with User in parameter, and
	 * redirect to list.
	 * 
	 * @param Integer       the id of the User you want to update.
	 * @param User          the new User data to update.
	 * @param BindingResult verification if User in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model)
			throws RessourceNotFoundException {
		String info = "UPDATE - Update User " + id + ".";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			user.setId(id);
			userService.updateUser(user);

			model.addAttribute("users", userService.findAllUsers());
			return "redirect:/user/list";
		} else {
			info = info + " Fail: User id is not valid.";
			LOGGER.info(info);

			return "user/update";
		}
	}

	/**
	 * Delete User with id in parameter from DataBase, and redirect to list.
	 * 
	 * @param Integer the id of the User you want to delete.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "DELETE - Delete User " + id + ".";
		LOGGER.info(info);

		User user = userService.findById(id);
		userService.deleteUser(user);

		model.addAttribute("users", userService.findAllUsers());
		return "redirect:/user/list";
	}
}
