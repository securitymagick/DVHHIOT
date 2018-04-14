package com.securitymagick.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.securitymagick.domain.LogMessage;
import com.securitymagick.domain.RegistrationForm;
import com.securitymagick.domain.dao.LogDao;
import com.securitymagick.domain.dao.UserDao;

@Controller
public class RegisterController {
	@Autowired
	UserDao userDao;
	
	@Autowired
	LogDao logDao;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationForm(Model model) {
		RegistrationForm registrationForm = new RegistrationForm();
		model.addAttribute("registerForm", registrationForm);
		return "redirect:/?register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String showAccount(@ModelAttribute("registerForm") RegistrationForm registrationForm,
		BindingResult result, Model model, HttpServletResponse response, HttpServletRequest request) {	
		if (registrationForm.getUsername().isEmpty() || registrationForm.getPassword().isEmpty() || registrationForm.getAnswer().isEmpty() || registrationForm.getQuestion().isEmpty() || registrationForm.getHabit().isEmpty()) {
			return "redirect:/?register=yes&message=One or more required form fields is empty.  Please check and try again.";
		}
		if (registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {	
			userDao.addUser(registrationForm);	
			LogMessage lm = new LogMessage(null, registrationForm.getUsername(), request.getHeader("user-agent"), "Registered new user");	
			logDao.addLog(lm);
			return "redirect:/?login=yes&message=Registration successful.  Please log in.";
		} else {
			String message = "Passwords do not match!";
			request.setAttribute("registerMessage", message);
			return "redirect:/?register=yes&message=" + message;
		}
	}
	


}