package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
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
	 */
	@Override
	public User addUser(User user) {
		String info = "Adding User in DataBase.";
		LOGGER.info(info);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	/**
	 * Update User with User id in parameter in DataBase with User in parameter.
	 *
	 * @param User the new User data we want to update.
	 * @return User the updated User.
	 * @throw RessourceNotFoundException when User id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public User updateUser(User user) throws RessourceNotFoundException {
		String info = "Updating User in DataBase.";
		LOGGER.info(info);

		if (userRepository.existsById(user.getId())) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			return userRepository.save(user);
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

}
