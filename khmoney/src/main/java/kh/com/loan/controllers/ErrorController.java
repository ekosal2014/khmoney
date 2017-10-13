package kh.com.loan.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.com.loan.domains.Message;
import kh.com.loan.utils.KHException;

@ControllerAdvice
public class ErrorController {
	
	@ExceptionHandler(KHException.class)
	public @ResponseBody Message messageException(KHException e) {
		return new Message(e.getCode(), e.getMessage());
	}
	
}
