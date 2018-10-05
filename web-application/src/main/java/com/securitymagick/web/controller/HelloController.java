package com.securitymagick.web.controller;


import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;

import com.securitymagick.web.cookie.CookieHandler;
import com.securitymagick.domain.Permissions;
import com.securitymagick.web.cookie.PermissionsCookie;
import com.securitymagick.domain.AuthToken;
import com.securitymagick.domain.ForgotPasswordToken;
import com.securitymagick.domain.LoginForm;
import com.securitymagick.domain.UsernameForm;
import com.securitymagick.domain.RegistrationForm;
import com.securitymagick.domain.User;
import com.securitymagick.domain.dao.UserDao;
import com.securitymagick.service.ForgotPasswordTokenChecker;
import com.securitymagick.domain.ResetPasswordForm;

@Controller
public class HelloController {
	private static final String USER_COOKIE_NAME = "user";
	public static final String UnExpectedErrorMessage = "An unexpected error occurred.  Please contact the admin.";	
	public static final String ResetPasswordInValidRandomToken = "Your token is expired or has already been used.";	
	public static final String FORGOTPASSWORD_COOKIE_NAME = "FORGOTPASSWORD";
	private static final String MESSAGE_ATTRIBUTE = "message";
	private static final String HELLO_PAGE = "hello";
	private static final String HABIT_HELPER_TITLE = "Habit Helper";
	private static final String TITLE_ATTRIBUTE_NAME = "title";
	private static final String RESETPASSWORD = "resetpassword";
	private static final String FORGOTPASSWORD = "forgotpassword";
	private static final String REGISTER = "register";
	private static final String LOGIN = "login";
	private static final String HOW = "how";
	private static final String ABOUT = "about";
	
	@Autowired
	ForgotPasswordTokenChecker tokenChecker;
	
	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}
		request.setAttribute(ABOUT, "");
		request.setAttribute(HOW, "");
		request.setAttribute(LOGIN, "");
		request.setAttribute(REGISTER, "");
		request.setAttribute(FORGOTPASSWORD, "");
		request.setAttribute(RESETPASSWORD, "");			
		model.addAttribute(TITLE_ATTRIBUTE_NAME, HABIT_HELPER_TITLE);
		return HELLO_PAGE;

	}

	@RequestMapping(value = "/", method = RequestMethod.GET,  params={ABOUT})
	public String printAbout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}
		request.setAttribute(ABOUT, ABOUT);
		request.setAttribute(HOW, "");
		request.setAttribute(LOGIN, "");
		request.setAttribute(REGISTER, "");
		request.setAttribute(FORGOTPASSWORD, "");
		request.setAttribute(RESETPASSWORD, "");			
		model.addAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper -- About");
		return HELLO_PAGE;

	}	

	@RequestMapping(value = "/", method = RequestMethod.GET,  params={HOW})
	public String printHow(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}
		request.setAttribute(ABOUT, "");
		request.setAttribute(HOW, HOW);
		request.setAttribute(LOGIN, "");
		request.setAttribute(REGISTER, "");
		request.setAttribute(FORGOTPASSWORD, "");
		request.setAttribute(RESETPASSWORD, "");			
		model.addAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper -- How It Works");
		return HELLO_PAGE;

	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET,  params={LOGIN})
	public String printLogin(@ModelAttribute("loginForm") LoginForm loginForm,
							@RequestParam(value = MESSAGE_ATTRIBUTE, required = false) String message,
							BindingResult result, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}
		if (message != null) {
			request.setAttribute(MESSAGE_ATTRIBUTE, message);
		}		
		request.setAttribute(ABOUT, "");
		request.setAttribute(HOW, "");
		request.setAttribute(LOGIN, LOGIN);
		request.setAttribute(REGISTER, "");
		request.setAttribute(FORGOTPASSWORD, "");
		request.setAttribute(RESETPASSWORD, "");			
		model.addAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper -- Login");
		return HELLO_PAGE;

	}	

	@RequestMapping(value = "/", method = RequestMethod.GET,  params={REGISTER})
	public String printRegister(@ModelAttribute("registerForm") RegistrationForm registrationForm,
								@RequestParam(value = MESSAGE_ATTRIBUTE, required = false) String message,
								BindingResult result, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}
		if (message != null) {
			request.setAttribute(MESSAGE_ATTRIBUTE, message);
		}		
		request.setAttribute(ABOUT, "");
		request.setAttribute(HOW, "");
		request.setAttribute(LOGIN, "");
		request.setAttribute(REGISTER, REGISTER);
		request.setAttribute(FORGOTPASSWORD, "");
		request.setAttribute(RESETPASSWORD, "");			
		model.addAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper -- Register");
		return HELLO_PAGE;
	}	

	@RequestMapping(value = "/", method = RequestMethod.GET,  params={FORGOTPASSWORD})
	public String printForgotPassword(@ModelAttribute("usernameForm") UsernameForm usernameForm, 
									@RequestParam(value = MESSAGE_ATTRIBUTE, required = false) String message,
									BindingResult result, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}
		if (message != null) {
			request.setAttribute(MESSAGE_ATTRIBUTE, message);
		}		
		request.setAttribute(ABOUT, "");
		request.setAttribute(HOW, "");
		request.setAttribute(LOGIN, "");
		request.setAttribute(REGISTER, "");
		request.setAttribute(FORGOTPASSWORD, FORGOTPASSWORD);
		request.setAttribute(RESETPASSWORD, "");		
		model.addAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper -- Forgot Password");
		
		if (request.getSession().getAttribute("resetpasswordEmail") != null) {
			String resetpasswordEmail = (String) request.getSession().getAttribute("resetpasswordEmail");
			if (!resetpasswordEmail.isEmpty()) {
				model.addAttribute("resetpasswordEmail", resetpasswordEmail);
			}
		}
		return HELLO_PAGE;

	}	

	@RequestMapping(value = "/", method = RequestMethod.GET,  params={RESETPASSWORD, "token"})
	public String showResetPasswordWihRandomToken(@ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm,
			@RequestParam(value = MESSAGE_ATTRIBUTE, required = false) String message, BindingResult result,
			@RequestParam(value = RESETPASSWORD) String user, @RequestParam(value = "token") String token, 
			 HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		if (user == null || user.isEmpty()) {
			return "redirect:/?forgotpassword=yes&message=" + UnExpectedErrorMessage;
		}
		String username = "";
		Boolean found = false;
		AuthToken aToken = new AuthToken(user);
		if (aToken.parseToken()) {
			List<User> ulist = userDao.getUsers();			
			for (User u: ulist) {
				if (u.getId().equals(aToken.getUid())) {
					username = u.getUsername();
					request.setAttribute("question", u.getQuestion());
					found = true;
				}
			}
		}
		if (!found) {
			return "redirect:/?forgotpasswordyes&message=" + UnExpectedErrorMessage;
		}
		ForgotPasswordToken forgottoken = new ForgotPasswordToken(token);
		if (!tokenChecker.isTokenValid(forgottoken, username)) {
			return "redirect:/?forgotpassword=yes&message=" + ResetPasswordInValidRandomToken;
		}
		
		CookieHandler userCookie = new CookieHandler(USER_COOKIE_NAME);
		userCookie.addCookie(response, user);
		CookieHandler fptCookie = new CookieHandler(FORGOTPASSWORD_COOKIE_NAME);
		fptCookie.addCookie(response, token);		

		request.setAttribute(ABOUT, "");
		request.setAttribute(HOW, "");
		request.setAttribute(LOGIN, "");
		request.setAttribute(REGISTER, "");
		request.setAttribute(FORGOTPASSWORD, "");
		request.setAttribute(RESETPASSWORD, RESETPASSWORD);
		request.setAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper Reset Password");
		return HELLO_PAGE;

	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET,  params={RESETPASSWORD})
	public String printResetPassword(@ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm,
													@RequestParam(value = MESSAGE_ATTRIBUTE, required = false) String message,
													BindingResult result, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}
		if (message != null && !message.isEmpty()) {
			request.setAttribute(MESSAGE_ATTRIBUTE, message);
		}		
		CookieHandler userCookie = new CookieHandler(USER_COOKIE_NAME);
		if (userCookie.checkForCookie(request)) {
			Cookie c = userCookie.getCookie();
			AuthToken aToken = new AuthToken(c.getValue());
			if (aToken.parseToken()) {
				List<User> ulist = userDao.getUsers();
				Boolean found = false;
				for (User u: ulist) {
					if (u.getId().equals(aToken.getUid())) {	
						request.setAttribute("question", u.getQuestion());
						found = true;
					}
				}
				if (!found) {
					request.setAttribute(MESSAGE_ATTRIBUTE, "Unexpected error.  Please Try again.");
					return HELLO_PAGE;		
				}
			} else {
				request.setAttribute(MESSAGE_ATTRIBUTE, "Unexpected error.  Please Try again.");
				return HELLO_PAGE;		
			}
		}		
		request.setAttribute(ABOUT, "");
		request.setAttribute(HOW, "");
		request.setAttribute(LOGIN, "");
		request.setAttribute(REGISTER, "");
		request.setAttribute(FORGOTPASSWORD, "");
		request.setAttribute(RESETPASSWORD, RESETPASSWORD);
		request.setAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper Reset Password");
		return HELLO_PAGE;

	}
	
	@RequestMapping(value = "/mobile/{page:.+}", method = RequestMethod.GET)
	public String hello(HttpServletRequest request, HttpServletResponse response, @PathVariable("page") String page) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}
		CookieHandler mobileCookie = new CookieHandler("mobile");
		mobileCookie.addCookie(response, "true");
	
		switch(page) {
		case "register":
			return "redirect:/?register";
		case "about":
			return "redirect:/?about";
		case "how":
			return "redirect:/?how"; 
		case "myAccount":
			return "redirect:hhelper://myAccount";
		case "therapist":
			return "redirect:/myAccount?therapist";
		case "forum":
			return "redirect:/public";
		case "score":
			return "redirect:hhelper://score";
		case "resist":
			return "redirect:hhelper://resist";
		case "update":
			return "redirect:/myAccount?update";
		case "forgotpassword":
			return "redirect:/?forgotpassword";
		case "resetpassword":
			return "redirect:/?resetpassword";
		}

		return "redirect:hhelper://open";

	}

}