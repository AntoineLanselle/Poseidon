package com.nnk.springboot.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.services.UserService;
import com.nnk.springboot.domain.User;

/**
 * Controller for ErrorPage.
 * 
 * @author Antoine Lanselle
 */
@Controller
public class ErrorPageController implements ErrorController {

	private static final Logger LOGGER = LogManager.getLogger(ErrorPageController.class);

	@Autowired
	private UserService userService;

	/**
	 * Return the template page of the error in request in parameter.
	 * 
	 * @param Model for adding attributes.
	 * @return String the template path.
	 */
	@RequestMapping(path = "/error")
	public String handleError(Model model, HttpServletRequest request) {
		String info = "GET - Error page.";
		LOGGER.info(info);

		User user = userService.getCurrentUser();

		model.addAttribute("userName", user.getUsername());
		model.addAttribute("role", user.getRole());
		String errorTitle = "An error occured";
		String errorMessage = "An unknown error occurred, try again.";
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				errorTitle = "Not Found Exception";
				errorMessage = "The page you are looking for does not exist.";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				errorTitle = "Access Denied Exception";
				errorMessage = "You do not have the rights to access this page.";
			}
		}
		model.addAttribute("errorTitle", errorTitle);
		model.addAttribute("errorMsg", errorMessage);
		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
