package com.securitymagick.web.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.securitymagick.domain.AuthToken;
import com.securitymagick.domain.BadRandomToken;
import com.securitymagick.domain.ForgotPasswordToken;
import com.securitymagick.domain.GoodRandomToken;
import com.securitymagick.domain.RandomToken;
import com.securitymagick.domain.User;
import com.securitymagick.domain.UsernameForm;
import com.securitymagick.domain.dao.UserDao;
import com.securitymagick.service.ForgotPasswordTokenChecker;
import com.securitymagick.web.cookie.CookieHandler;

@Controller
public class ForgotPasswordController {
	public static final String ForgotPasswordEmailMessage = "An email with instructions to reset your password has been sent.";
	public static final String ForgotPasswordMessage = "Follow the instructions to reset your password.";
	public static final String ResetPasswordEmailMessageText = "Click the following link to reset your password: ";
	public static final String ResetPasswordEmailLinkText = "Reset Your Password";
	public static final Long FORGOT_PASSWORD_TOKEN_EXPIRATION_IN_SECONDS = 300L;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ForgotPasswordTokenChecker tokenChecker;

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String showForgotPassword(Model model, @RequestParam(value = "message", required = false) String message, HttpServletRequest request) {
		UsernameForm usernameForm = new UsernameForm();
		model.addAttribute("usernameForm", usernameForm);
		if (message != null) {
			request.setAttribute("message", message);
		}
		return "redirect:/?forgotpassword=yes&message=" + message;	
	}	
	
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public String checkAccount(@ModelAttribute("usernameForm") UsernameForm usernameForm,
		BindingResult result, Model model, HttpServletResponse response, HttpServletRequest request) {

		String message = ForgotPasswordEmailMessage;
		CookieHandler userCookie = new CookieHandler("user");
		List<User> ulist = userDao.getUser(usernameForm.getUsername());
		
		if (ulist.size() != 1) {
			message = "The username you entered is not registered.";
			request.setAttribute("message", message);
			userCookie.addCookie(response, "");
			return "redirect:/?forgotpassword=yes&message=" + message;	
		}
		
		User u = ulist.get(0);
		AuthToken aToken = new AuthToken(u.getId());
		String resetPasswordToken =  aToken.getToken();
		String resetRandomToken = "";
		
		RandomToken r;		
		r = new GoodRandomToken();			
		ForgotPasswordToken fPToken = new ForgotPasswordToken(r.getNewToken(),u.getUsername(),FORGOT_PASSWORD_TOKEN_EXPIRATION_IN_SECONDS);
		tokenChecker.addToken(fPToken);
		resetRandomToken = fPToken.toString();
		resetPasswordToken += "&token=" + resetRandomToken;
		
		String resetpasswordEmail = ResetPasswordEmailMessageText + "<a href=\"?resetpassword=" + resetPasswordToken + "\">" + ResetPasswordEmailLinkText + "</a>";
		request.getSession().setAttribute("resetpasswordEmail", resetpasswordEmail);	
		//userCookie.addCookie(response, aToken.getToken());
		//model.addAttribute("resetpasswordEmail", resetpasswordEmail);
		//request.setAttribute("message", message);
		return "redirect:/?forgotpassword=yes&message=" + message;	

	}
	
}