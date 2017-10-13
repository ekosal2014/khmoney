package kh.com.loan.domains;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class User implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int user_id;
	private String user_code;
	private String full_name;
	private String gender;
	private String phone;
	private String email;
	private String address;
	private String username;
	private String password;
	private int createby;
	private String createdt;
	private String photo;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_code=" + user_code + ", full_name=" + full_name + ", gender="
				+ gender + ", phone=" + phone + ", email=" + email + ", address=" + address + ", username=" + username
				+ ", password=" + password + ", createby=" + createby + ", createdt=" + createdt + ", photo=" + photo
				+ ", sts=" + sts + ", txt=" + txt + ", roles=" + roles + "]";
	}
	public User(int user_id, String user_code, String full_name, String gender, String phone, String email,
			String address, String username, String password, int createby, String createdt, String photo, String sts,
			String txt, List<Role> roles) {
		super();
		this.user_id = user_id;
		this.user_code = user_code;
		this.full_name = full_name;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.username = username;
		this.password = password;
		this.createby = createby;
		this.createdt = createdt;
		this.photo = photo;
		this.sts = sts;
		this.txt = txt;
		this.roles = roles;
	}
	private String sts;
	private String txt;
	private List<Role> roles; 
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCreateby() {
		return createby;
	}
	public void setCreateby(int createby) {
		this.createby = createby;
	}
	public String getCreatedt() {
		return createdt;
	}
	public void setCreatedt(String createdt) {
		this.createdt = createdt;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		if ( sts.equals("1")) {
			return true;
		}
		return false;
	}
	
}
