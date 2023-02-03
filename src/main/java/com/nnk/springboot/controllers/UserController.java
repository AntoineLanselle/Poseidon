package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.UserService;

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
 * Controller for User.
 * 
 * @author Antoine Lanselle
 */
@Controller
@RequestMapping(path = "/user")
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
	@GetMapping(path = "/list")
	public String showUsers(Model model) {
		String info = "GET - User List page.";
		LOGGER.info(info);

		User user = userService.getCurrentUser();
		
		model.addAttribute("userName", user.getUsername());
		model.addAttribute("role", user.getRole());
		model.addAttribute("users", userService.findAllUsers());
		return "user/list";
	}

	/**
	 * Show the form to add a User.
	 * 
	 * @param User the new User to be completed by the user.
	 * @return String of the template path.
	 */
	@GetMapping(path = "/add")
	public String addUserForm(User user) {
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
	@PostMapping(path = "/validate")
	public String validateUser(@Valid User user, BindingResult result, Model model) {
		String info = "POST - Add new User.";

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
	@GetMapping(path = "/update/{id}")
	public String showUpdateUserForm(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
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
	@PostMapping(path = "/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model)
			throws RessourceNotFoundException {
		String info = "POST - Update User " + id + ".";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			user.setId(id);
			userService.updateUser(user);

			model.addAttribute("users", userService.findAllUsers());
			return "redirect:/user/list";
		} else {
			info = info + " Fail: new User datas are not valid.";
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
	@GetMapping(path = "/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Delete User " + id + ".";
		LOGGER.info(info);

		User user = userService.findById(id);
		userService.deleteUser(user);

		model.addAttribute("users", userService.findAllUsers());
		return "redirect:/user/list";
	}

	/**
	 * API request - Return all Users in DataBase.
	 * 
	 * @return ResponseEntity<List<User> a response with https status OK and a User
	 *         List of all Users in DataBase as body.
	 */
	@GetMapping(path = "/all")
	public ResponseEntity<List<User>> getUsers() {
		String info = "API GET - User List of all Users.";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsers());
	}

	/**
	 * API request - Return the User with id in parameter.
	 * 
	 * @param Integer the id of the User you want to get.
	 * @return ResponseEntity<User> a response with https status OK and a User as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping()
	public ResponseEntity<User> getUser(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API GET - User with id: " + id + ".";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
	}

	/**
	 * API request - Add the User in parameter in DataBase and return https status
	 * OK with message.
	 * 
	 * @param User the User data to add in DataBase.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 */
	@PostMapping()
	public ResponseEntity<String> addUser(@RequestBody User user) {
		String info = "API POST - Add User.";
		LOGGER.info(info);

		userService.addUser(user);
		String msg = "User has been added in DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	/**
	 * API request - Update the User with id in parameter in DataBase with User data
	 * in parameter and return https status OK with message.
	 * 
	 * @param User the User data to put in DataBase.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PutMapping()
	public ResponseEntity<String> updateUser(@RequestBody User user) throws RessourceNotFoundException {
		String info = "API UPDATE - Update User.";
		LOGGER.info(info);

		userService.updateUser(user);
		String msg = "User has been updated in DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	/**
	 * API request - Delete User with id in parameter from DataBase.
	 * 
	 * @param Integer the id of the User you want to delete.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 * 
	 */
	@DeleteMapping()
	public ResponseEntity<String> deleteUser(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API DELETE - User with id: " + id + ".";
		LOGGER.info(info);

		userService.deleteUser(userService.findById(id));
		String msg = "User with id: " + id + " has been delete from DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}
}
