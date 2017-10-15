package kh.com.loan.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import kh.com.loan.services.CustomUserDetailsService;



@Configuration
public class KHSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
	@Autowired
	private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
	
	
	// Use customUserDetailsService
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
/*				.antMatchers("/","/home","/about","/article","/user").permitAll()*/
				.antMatchers("/article/**").hasAnyRole("USER","ADMIN")
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
			    .antMatchers("/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.failureUrl("/login?error")
				//.successForwardUrl("/home")
				.successHandler(ajaxAuthenticationSuccessHandler)
				.failureHandler(ajaxAuthenticationFailureHandler)
				//.failureForwardUrl("/login")
				.permitAll()
			.and()
				.logout()
				.permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/error/403");
			//.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth
			.inMemoryAuthentication()
				.withUser("user").password("123").roles("USER")
				.and()
				.withUser("admin").password("123").roles("ADMIN");	*/	
	
		
		auth.userDetailsService(userDetailsService);
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
		web.ignoring().antMatchers("/static/**");
	}
	
	
	
}
