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

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserService userService;
	@Mock
	private Model model;
	@InjectMocks
	private UserController userController;

	@Test
	public void showUsers_ShouldReturnStringOfTemplatePath() {
		// GIVEN
		User user = new User();
		user.setRole("USER");
		when(userService.getCurrentUser()).thenReturn(user);
		when(userService.findAllUsers()).thenReturn(new ArrayList<User>());

		// WHEN
		String testResult = userController.showUsers(model);

		// THEN
		assertEquals("user/list", testResult);
	}

	@Test
	public void addUserForm_ShouldReturnStringOfTemplatePath() {
		// GIVEN

		// WHEN
		String testResult = userController.addUserForm(new User());

		// THEN
		assertEquals("user/add", testResult);
	}

	@Test
	public void validate_ShouldRedirectToUser() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = userController.validateUser(new User(), result, model);

		// THEN
		assertEquals("redirect:/user/list", testResult);
	}

	@Test
	public void validate_ShouldStringOfTemplatePathUserAdd() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = userController.validateUser(new User(), result, model);

		// THEN
		assertEquals("user/add", testResult);
	}

	@Test
	public void showUpdateUserForm_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		when(userService.findById(1)).thenReturn(new User());

		// WHEN
		String testResult = userController.showUpdateUserForm(1, model);

		// THEN
		assertEquals("user/update", testResult);
	}

	@Test
	public void updateUser_ShouldRedirectToUser() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = userController.updateUser(1, new User(), result, model);

		// THEN
		assertEquals("redirect:/user/list", testResult);
	}

	@Test
	public void updateUser_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = userController.updateUser(1, new User(), result, model);

		// THEN
		assertEquals("user/update", testResult);
	}

	@Test
	public void deleteUser_ShouldRedirectToUser() throws RessourceNotFoundException {
		// GIVEN
		when(userService.findById(1)).thenReturn(new User());
		when(userService.findAllUsers()).thenReturn(new ArrayList<User>());

		// WHEN
		String testResult = userController.deleteUser(1, model);

		// THEN
		assertEquals("redirect:/user/list", testResult);
	}

	@Test
	public void getUser_ShouldReturnResponseEntityWithStatusOKAndListOfUsersAsBody() throws RessourceNotFoundException {
		// GIVEN
		User user = new User();
		List<User> users = new ArrayList<User>();
		users.add(user);
		when(userService.findAllUsers()).thenReturn(users);

		// WHEN
		ResponseEntity<List<User>> testResult = userController.getUsers();

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(1, testResult.getBody().size());
		assertTrue(testResult.getBody().contains(user));
	}

	@Test
	public void getUser_ShouldReturnResponseEntityWithStatusOKAndUserAsBody() throws RessourceNotFoundException {
		// GIVEN
		User user = new User();
		when(userService.findById(1)).thenReturn(user);

		// WHEN
		ResponseEntity<User> testResult = userController.getUser(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(user, testResult.getBody());
	}

	@Test
	public void addCurvePoint_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		User user = new User();

		// WHEN
		ResponseEntity<String> testResult = userController.addUser(user);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("User has been added in DataBase.", testResult.getBody());
	}

	@Test
	public void updateCurvePoint_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		User user = new User();

		// WHEN
		ResponseEntity<String> testResult = userController.updateUser(user);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("User has been updated in DataBase.", testResult.getBody());
	}

	@Test
	public void deleteUser_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN

		// WHEN
		ResponseEntity<String> testResult = userController.deleteUser(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("User with id: 1 has been delete from DataBase.", testResult.getBody());
	}

}
