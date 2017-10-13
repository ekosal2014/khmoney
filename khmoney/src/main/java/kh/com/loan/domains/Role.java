package kh.com.loan.domains;

import java.sql.Timestamp;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Tola
 *	Created Date: 2017/10/27
 */
public class Role implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role;
	}
	
	
	

	
	
}
