package kh.com.loan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.loan.domains.User;
import kh.com.loan.mappers.UserMapper;
import kh.com.loan.utils.KHException;



@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public User loadUserByUsername (String username) {		
		return userMapper.loadUserByUsername(username);		
		
	}
	
	

}
