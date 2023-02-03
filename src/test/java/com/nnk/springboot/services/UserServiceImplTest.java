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

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.UserRepository;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserServiceImpl userService;
	private static List<User> userRepo;
	
	@BeforeAll
	public static void init() {
		User userOne = new User();
		userOne.setPassword("password");
		User userTwo = new User();
		userTwo.setPassword("password");
		userRepo = new ArrayList<User>();
		userRepo.add(userOne);
		userRepo.add(userTwo);
	}
	
	@Test
	public void findAllUsers_ShouldReturnListOfAllUsers() {
		// GIVEN
		when(userRepository.findAll()).thenReturn(userRepo);
		
		// WHEN
		List<User> testResult = userService.findAllUsers();
		
		// THEN
		assertEquals(userRepo, testResult);
	}
	
	@Test
	public void findById_ShouldReturnSpecificUser() throws RessourceNotFoundException {
		// GIVEN
		Optional<User> optUser = Optional.of(userRepo.get(0));
		when(userRepository.findById(1)).thenReturn(optUser);
		
		// WHEN
		User testResult = userService.findById(1);
		
		// THEN
		assertEquals(userRepo.get(0), testResult);
	}
	
	@Test
	public void findById_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		Optional<User> userNull = Optional.empty();
		when(userRepository.findById(1)).thenReturn(userNull);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			userService.findById(1);
		});
	}
	
	@Test
	public void addUser_ShouldReturnUserInParameter() throws RessourceNotFoundException {
		// GIVEN
		when(userRepository.save(userRepo.get(0))).thenReturn(userRepo.get(0));
		
		// WHEN 
		User testResult = userService.addUser(userRepo.get(0));
		
		// THEN
		assertEquals(userRepo.get(0), testResult);
	}
	
	@Test
	public void updateUser_ShouldReturnUserInParameter() throws RessourceNotFoundException {
		// GIVEN
		User user = new User(); 
		user.setPassword("password");
		when(userRepository.existsById(user.getId())).thenReturn(true);
		when(userRepository.save(user)).thenReturn(user);
		
		// WHEN 
		User testResult = userService.updateUser(user);
		
		// THEN
		assertEquals(user, testResult);
	}
	
	@Test
	public void updateUser_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		User user = new User(); 
		user.setId(404);
		when(userRepository.existsById(404)).thenReturn(false);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			userService.updateUser(user);
		});
	}
	
	@Test
	public void deleteUser_ShouldReturnUserInParameter() throws RessourceNotFoundException {
		// GIVEN
		User user = new User(); 
		when(userRepository.existsById(user.getId())).thenReturn(true);
		
		// WHEN
		userService.deleteUser(user);
		
		// THEN
		verify(userRepository).delete(user);
	}
	
	@Test
	public void deleteUser_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		User user = new User(); 
		user.setId(404);
		when(userRepository.existsById(404)).thenReturn(false);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			userService.deleteUser(user);
		});
	}
	
	@Test
	public void findByUsername_shouldReturnUserWithUsernameInParameter() {
		// GIVEN
		User user = new User();
		String username = "Test";
		when(userRepository.findByUsername(username)).thenReturn(user);
		
		// WHEN
		User testResult = userService.findByUsername(username);
		
		//THEN
		assertEquals(user, testResult);
	}
	
}
