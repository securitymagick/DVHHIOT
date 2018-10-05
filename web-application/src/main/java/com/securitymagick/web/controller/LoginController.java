package com.securitymagick.web.controller;

import static com.securitymagick.AppConstants.CSRF_COOKIE_NAME;
import static com.securitymagick.AppConstants.csrfProtection;
import static com.securitymagick.AppConstants.csrfProtectionGoodRandom;
import static com.securitymagick.AppConstants.localStorageCsrfProtection;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.securitymagick.domain.AuthToken;
import com.securitymagick.domain.BadRandomToken;
import com.securitymagick.domain.GoodRandomToken;
import com.securitymagick.domain.LogMessage;
import com.securitymagick.domain.LoginForm;
import com.securitymagick.domain.Notifications;
import com.securitymagick.domain.Permissions;
import com.securitymagick.domain.RandomToken;
import com.securitymagick.domain.User;
import com.securitymagick.domain.dao.LogDao;
import com.securitymagick.domain.dao.UserDao;
import com.securitymagick.web.cookie.CookieHandler;

@Controller
public class LoginController {	
	
	private static final String USER_AGENT = "user-agent";

	private static final String PERMISSIONS = "permissions";

	private static final String MESSAGE_ATTRIBUTE = "message";

	@Autowired
	UserDao userDao;
	
	@Autowired
	LogDao logDao;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String showAccount(@ModelAttribute("loginForm") LoginForm loginForm,
		BindingResult result, Model model, HttpServletResponse response, HttpServletRequest request) {
		LogMessage lm = new LogMessage(null, null, request.getHeader(USER_AGENT), "Trying to log in user with credentials: " + loginForm.toString());	
		logDao.addLog(lm);
		
		CookieHandler pCookie = new CookieHandler(PERMISSIONS);
		CookieHandler userCookie = new CookieHandler("user");
		
		List<User> ulist = userDao.getUser(loginForm.getUsername());
		String message = "An unexpected error occurred.  Please contact the Bob or Harry.";
		if (ulist.size() != 1) {		
			request.setAttribute(MESSAGE_ATTRIBUTE, message);
			return "redirect:/?login=yes&" + MESSAGE_ATTRIBUTE + "="+message;			
		}
		
		String rememberMeToken = "";
		User u = ulist.get(0);
		Boolean loggedOn = false;
		if (loginForm.isRememberme() && !loginForm.getRememberMeToken().isEmpty()) {
			 try {
					MessageDigest md5 = MessageDigest.getInstance("MD5");
					md5.update(StandardCharsets.UTF_8.encode(u.getPassword()));
					rememberMeToken = String.format("%032x", new BigInteger(1, md5.digest()));
				} catch (NoSuchAlgorithmException e) {
					request.setAttribute(MESSAGE_ATTRIBUTE, message);
					return "redirect:/?login=yes&" + MESSAGE_ATTRIBUTE + "="+message;		
				}
			 if (loginForm.getRememberMeToken().equals(rememberMeToken)) {
				 loggedOn = true;
			 }
		}
		
		if (loginForm.getPassword().equals(u.getPassword())) {
			loggedOn = true;
		}
		
		if (loggedOn) {
			Permissions p = new Permissions(u.getIsUser().equals(1), u.getIsAdmin().equals(1));
			pCookie.addCookie(response, p.getCookieValue());
			AuthToken aToken = new AuthToken(u.getId());
			userCookie.addCookie(response, aToken.getToken());	
			
			if (loginForm.isRememberme() && rememberMeToken.isEmpty()) {
				try {
					MessageDigest md5 = MessageDigest.getInstance("MD5");
					md5.update(StandardCharsets.UTF_8.encode(loginForm.getPassword()));
					rememberMeToken = "&rememberMeToken=" + String.format("%032x", new BigInteger(1, md5.digest()));
				} catch (NoSuchAlgorithmException e) {
					rememberMeToken = "";
				}
			} else {
				rememberMeToken= "";
			}
			
			RandomToken r;
			r = new BadRandomToken();
			String token = r.getNewToken();
			CookieHandler csrfCookie = new CookieHandler("CSRFTOKEN");	
			csrfCookie.addCookie(response, token);
			token = "?newCsrfToken=" + token;
				
			return "redirect:/myAccount" + token + rememberMeToken;			
			//return "redirect:/myAccount";
		} else {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p.getCookieValue());
			userCookie.addCookie(response, "");
			message = "Incorrect username or password.  If you forgot your password please use the forgot password link.";
			request.setAttribute(MESSAGE_ATTRIBUTE, message);
			return "redirect:?login=yes&" + MESSAGE_ATTRIBUTE + "=" + message;
		}
	}	
	
	@RequestMapping(value = "/loginAtRest/{username:.+}/{password:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String restLogin(HttpServletResponse response, HttpServletRequest request, @PathVariable("username") String username, @PathVariable("password") String password) {
		LogMessage lm = new LogMessage(null, null, request.getHeader(USER_AGENT), "Trying to log in user with credentials: " + username + " " + password);	
		logDao.addLog(lm);
		
		
		List<User> ulist = userDao.getUser(username);
		
		if (ulist.size() != 1) {
			return "{\"AuthToken\":\"ERROR\"}";
			
		}
		
		User u = ulist.get(0);
		
		if (password.equals(u.getPassword())) {
			AuthToken aToken = new AuthToken(u.getId());
			return "{\"AuthToken\":\""+ aToken.getToken() + "\"}";
			
		} else {
			return "{\"AuthToken\":\"ERROR\"}";
		}
	}		

	@RequestMapping(value = "/bypasslogin/{token:.+}/{page:.+}", method = RequestMethod.GET)
	public String restByPass(HttpServletResponse response, HttpServletRequest request, @PathVariable("token") String token, @PathVariable("page") String page) {
		LogMessage lm = new LogMessage(null, null, request.getHeader(USER_AGENT), "Logging in user and forwarding: " + token + ", " + page);	
		logDao.addLog(lm);

		CookieHandler pCookie = new CookieHandler(PERMISSIONS);
		CookieHandler userCookie = new CookieHandler("user");		
		AuthToken aToken = new AuthToken(token);
		if (aToken.parseToken()) {
			List<User> ulist = userDao.getUsers();
			for (User u: ulist) {
				if (u.getId().equals(aToken.getUid())) {
					Permissions p = new Permissions(u.getIsUser().equals(1), u.getIsAdmin().equals(1));
					pCookie.addCookie(response, p.getCookieValue());
					userCookie.addCookie(response, aToken.getToken());
				}
			}
		} 
		return "redirect:/mobile/"+ page;
	}			
	
	@RequestMapping(value = "/validateToken/{token:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String tokenValidate(HttpServletResponse response, HttpServletRequest request, @PathVariable("token") String token) {
		LogMessage lm = new LogMessage(null, null, request.getHeader(USER_AGENT), "Trying to validate token: " + token);	
		logDao.addLog(lm);
		
		AuthToken aToken = new AuthToken(token);
		if (aToken.parseToken()) {
			List<User> ulist = userDao.getUsers();
			for (User u: ulist) {
				if (u.getId().equals(aToken.getUid())) {
					return "{\"User\":\""+ u.getUsername() + "\"}";
				}
			}
		} 
		return "{\"User\":\"" + aToken.getHelper() + "\"}";
	}		
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm(Model model, @RequestParam(value = MESSAGE_ATTRIBUTE, required = false) String message, HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		if (message != null) {
			request.setAttribute(MESSAGE_ATTRIBUTE, message);
			query = "=yes&" + MESSAGE_ATTRIBUTE + "=" + message;
		}
		return "redirect:/?login" + query;
	}	
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutUser(Model model, HttpServletRequest request, HttpServletResponse response) {
		CookieHandler pCookie = new CookieHandler(PERMISSIONS);
		CookieHandler userCookie = new CookieHandler("user");	
		CookieHandler nCookie = new CookieHandler("notifications");	
		
		Permissions p = new Permissions();
		pCookie.addCookie(response, p.getCookieValue());
		userCookie.addCookie(response, "");		

		Notifications n = new Notifications();
		nCookie.addCookie(response, n.getCookieValue());
		
		return "redirect:/";
	}		

}