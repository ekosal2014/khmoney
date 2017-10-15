package kh.com.loan.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kh.com.loan.domains.Message;
import kh.com.loan.domains.User;
import kh.com.loan.services.UserService;
import kh.com.loan.utils.KHException;
import kh.com.loan.utils.SessionException;
import kh.com.loan.utils.SessionUtils;

@Controller
public class KHController {
	

	@RequestMapping("/login")
	public String loginPage(ModelMap model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!auth.getPrincipal().equals("anonymousUser")){			
			User user = (User) auth.getPrincipal();
			System.out.println(user.getFull_name());
			model.addAttribute("user",user);
			return "redirect:/khmoney/";
		}
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
	

}
