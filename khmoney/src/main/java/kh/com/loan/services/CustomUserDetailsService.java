package kh.com.loan.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kh.com.loan.domains.Role;
import kh.com.loan.domains.User;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired 
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
			System.out.println(" User Name = "+ username);
			User user = (User) userService.loadUserByUsername(username);
		
			
			if (user == null) {
				throw new UsernameNotFoundException("User not found!");
			}
			
			
			if (user.getTxt().equals("2")) {
				Role role = new Role();
				List<Role> roles = new ArrayList<>();
				role.setRole("ROLE_ADMIN");
				roles.add(role);
				user.setRoles(roles);
			}
			return user;
		
	}
	/*private List<GrantedAuthority> getGrantedAuthorities(User User){		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();				
		String txt = User.getTxt().equals("1") ? "AMIN" : "USER";
		authorities.add(new SimpleGrantedAuthority("ROLE_"+txt));
		return authorities;
	}*/
	
}
