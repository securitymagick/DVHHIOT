package com.securitymagick.web.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.securitymagick.domain.AuthToken;
import com.securitymagick.domain.LogMessage;
import com.securitymagick.domain.ResetPasswordForm;
import com.securitymagick.domain.User;
import com.securitymagick.domain.dao.LogDao;
import com.securitymagick.domain.dao.UserDao;
import com.securitymagick.web.cookie.CookieHandler;

@Controller
public class ResetPasswordController {
	@Autowired
	UserDao userDao;

	@Autowired
	LogDao logDao;
	
	@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	public String showResetPasswordForm(Model model, HttpServletRequest request) {
		ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
		CookieHandler userCookie = new CookieHandler("user");
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
					return "redirect:/?resetpassword=yes&message=Unexpected error.  Please Try again or contact Bob or Harry.";
				}
			} else {
				return "redirect:/?resetpassword=yes&message=Unexpected error.  Please Try again or contact Bob or Harry.";
			}
		}

		model.addAttribute("resetPasswordForm", resetPasswordForm);		
		return "redirect:/?resetpassword";
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public String checkAccount(@ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm,
		BindingResult result, Model model, HttpServletResponse response, HttpServletRequest request) {
	
		CookieHandler userCookie = new CookieHandler("user");
		if (userCookie.checkForCookie(request)) {
			Cookie c = userCookie.getCookie();
			if (resetPasswordForm.getPassword().equals(resetPasswordForm.getConfirmPassword())) {
				AuthToken aToken = new AuthToken(c.getValue());
				if (aToken.parseToken()) {
					List<User> ulist = userDao.getUsers();
					Boolean found = false;
					for (User u: ulist) {
						if (u.getId().equals(aToken.getUid())) {	
							found = true;
							if (u.getAnswer().equals(resetPasswordForm.getAnswer())) {
								LogMessage lm = new LogMessage(null, u.getUsername(), request.getHeader("user-agent"), "Password reset successful for user.");	
								logDao.addLog(lm);
								userDao.updatePassword(u.getUsername(), resetPasswordForm.getPassword());
								return "redirect:/?login=yes&message=Password updated.  Please login.";
							}
							else {
								LogMessage lm = new LogMessage(null, u.getUsername(), request.getHeader("user-agent"), "Password reset failed for user.");	
								logDao.addLog(lm);
								String message = "Incorrect information!";
								request.setAttribute("message", message);
								return "redirect:/?resetpassword=yes&message=" + message;	
							}
						}
					}
					if (!found) {
						return "redirect:/?resetpassword=yes&message=Unexpected error.  Please Try again or contact Bob or Harry.";
					}
				} else {
					return "redirect:/?resetpassword=yes&message=Unexpected error.  Please Try again or contact Bob or Harry.";
				}
					
			} else {
				String message = "Passwords do not match!";
				request.setAttribute("message", message);
				return "redirect:/?resetpassword=yes&message=" + message;	
			}
		} 
		return "redirect:/?forgotpassword=yes&message=Unexpected error.  Please Try again or contact Bob or Harry.";
		
	}


}