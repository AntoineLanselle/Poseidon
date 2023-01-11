package  com.nnk.springboot.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nnk.springboot.domain.User;

public interface UserService { //extends UserDetailsService {

	//UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	//User getCurrentUser();

	User findByUsername(String username);
	
}
