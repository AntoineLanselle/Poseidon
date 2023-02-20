package com.nnk.springboot.services;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.exceptions.AlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	/**
	 * Return all Users in DataBase.
	 *
	 * @return List<User> all the Users in the DataBase.
	 */
	@Override
	public List<User> findAllUsers() {
		String info = "Returning all Users from DataBase.";
		LOGGER.info(info);

		return userRepository.findAll();
	}

	/**
	 * Return specific User from DataBase with id in parameter.
	 *
	 * @param Integer the id of the User.
	 * @return User with id in parameter in DataBase.
	 * @throw RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@Override
	public User findById(Integer id) throws RessourceNotFoundException {
		String info = "Looking for User with id " + id + " in DataBase.";
		String error = "Fail: User with id " + id + " not found in DataBase.";
		LOGGER.info(info);

		return userRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException(error));
	}

	/**
	 * Add User in parameter in DataBase.
	 *
	 * @param User you want to add in DataBase.
	 * @return User the added Trade.
	 * @throws AlreadyExistException when username of user already exist.
	 */
	@Override
	public User addUser(User user) throws AlreadyExistException {
		String info = "Adding User in DataBase.";
		LOGGER.info(info);

		if (findByUsername(user.getUsername()) == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			return userRepository.save(user);
		} else {
			String error = "Fail: User with username " + user.getUsername() + " already exist in DataBase.";
			throw new AlreadyExistException(error);
		}
	}

	/**
	 * Update User with User id in parameter in DataBase with User in parameter.
	 *
	 * @param User the new User data we want to update.
	 * @return User the updated User.
	 * @throws AlreadyExistException when username of user already exist.
	 * @throw RessourceNotFoundException when User id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public User updateUser(User user) throws RessourceNotFoundException, AlreadyExistException {
		String info = "Updating User in DataBase.";
		LOGGER.info(info);

		if (userRepository.existsById(user.getId())) {
			// checking if the new username doesn't exist or already taken by this user
			if (findByUsername(user.getUsername()) == null
					|| findByUsername(user.getUsername()).getId() == user.getId()) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				user.setPassword(encoder.encode(user.getPassword()));
				return userRepository.save(user);
			} else {
				String error = "Fail: User with username " + user.getUsername() + " already exist in DataBase.";
				throw new AlreadyExistException(error);
			}
		} else {
			String error = "Fail: User with id " + user.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

	/**
	 * Delete User with User id in parameter from DataBase.
	 *
	 * @param User the User you want to delete from DataBase.
	 * @throw RessourceNotFoundException when User id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public void deleteUser(User user) throws RessourceNotFoundException {
		String info = "Deleting User in DataBase.";
		LOGGER.info(info);

		if (userRepository.existsById(user.getId())) {
			userRepository.delete(user);
		} else {
			String error = "Fail: User with id " + user.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

	/**
	 * Return specific User from DataBase with the String username in parameter.
	 *
	 * @param String the username of the User.
	 * @return User with String username in parameter in DataBase.
	 */
	@Override
	public User findByUsername(String username) {
		String info = "Looking for User with username " + username + " in DataBase.";
		LOGGER.info(info);

		return userRepository.findByUsername(username);
	}

	/**
	 * 
	 */
	@Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(auth.getName());
		if (user != null) {
			return user;
		} else if (auth.isAuthenticated()) {
			user = new User();
			user.setRole("USER");
			user.setUsername("GithubUser");
			return user;
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			String error = username + "not found";
			throw new UsernameNotFoundException(error);
		} else {
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
		}
	}

}
