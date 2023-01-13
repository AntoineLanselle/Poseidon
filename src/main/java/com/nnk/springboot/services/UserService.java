package  com.nnk.springboot.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;

public interface UserService { //extends UserDetailsService {

	//UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	//User getCurrentUser();

	User findByUsername(String username);
	
	public List<User> findAllUsers();

	public User findById(Integer id) throws RessourceNotFoundException;

	public User addUser(User user);

	public User updateUser(User user) throws RessourceNotFoundException;

	public void deleteUser(User user) throws RessourceNotFoundException;
	
}
