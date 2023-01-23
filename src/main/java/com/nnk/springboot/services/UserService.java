package  com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;

public interface UserService {

	User findByUsername(String username);
	
	public List<User> findAllUsers();

	public User findById(Integer id) throws RessourceNotFoundException;

	public User addUser(User user);

	public User updateUser(User user) throws RessourceNotFoundException;

	public void deleteUser(User user) throws RessourceNotFoundException;
	
}
