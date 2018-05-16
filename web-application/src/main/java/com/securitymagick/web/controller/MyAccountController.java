package com.securitymagick.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.securitymagick.domain.AdminDBItem;
import com.securitymagick.domain.AuthToken;
import com.securitymagick.domain.LogMessage;
import com.securitymagick.domain.UpdateHabitForm;
import com.securitymagick.domain.UpdatePasswordForm;
import com.securitymagick.domain.User;
import com.securitymagick.domain.dao.AdminDao;
import com.securitymagick.domain.dao.LogDao;
import com.securitymagick.domain.dao.UserDao;
import com.securitymagick.web.cookie.CookieHandler;


@Controller
public class MyAccountController {
	private static final String HABIT_HELPER_ACCOUNT_UPDATE = "Habit Helper Account Update";

	private static final String REDIRECT_LOGIN = "redirect:/login";

	private static final String THERAPIST = "therapist";

	private static final String RESIST = "resist";

	private static final String SCORE = "score";

	private static final String UPDATE = "update";

	private static final String MY_ACCOUNT_PAGE = "myAccount";

	private static final String TITLE = "title";

	private static final String SERVICE_URL = "serviceURL";

	@Autowired
	UserDao userDao;
	
	@Autowired
	LogDao logDao;
	
	@Autowired
	AdminDao adminDao;	

	@RequestMapping(value = "/myAccount", method = RequestMethod.GET)
	public String myAccount(HttpServletRequest request) {
		CookieHandler userCookie = new CookieHandler("user");
		if (!userCookie.checkForCookie(request)) {
			return REDIRECT_LOGIN;
		}
		List<AdminDBItem> items= adminDao.getAdminDB();
		for (AdminDBItem item: items) {
			if (item.getSettingName().equals(SERVICE_URL)) {
				request.setAttribute(item.getSettingName(), item.getSettingValue());
			}
		}
		request.setAttribute(UPDATE, "");
		request.setAttribute(SCORE, "");
		request.setAttribute(RESIST, "");
		request.setAttribute(THERAPIST, "");	
		request.setAttribute(TITLE, "Habit Helper -- MyAccount");			
		return MY_ACCOUNT_PAGE;

	}

	@RequestMapping(value = "/myAccount", method = RequestMethod.GET, params={SCORE})	
	public String showScoreboard(HttpServletRequest request) {
		CookieHandler userCookie = new CookieHandler("user");
		if (!userCookie.checkForCookie(request)) {
			return REDIRECT_LOGIN;
		}		
		if (userCookie.checkForCookie(request)) {
			Cookie c = userCookie.getCookie();
			AuthToken aToken = new AuthToken(c.getValue());
			if (aToken.parseToken()) {
				List<User> ulist = userDao.getUsers();
				Boolean found = false;
				for (User u: ulist) {
					if (u.getId().equals(aToken.getUid())) {				
						request.setAttribute("user", u.getUsername());
						found = true;
					}
				}
				if (!found) {
					return REDIRECT_LOGIN;
				}
			} else {
				return REDIRECT_LOGIN;
			}
		}
		
		List<AdminDBItem> items= adminDao.getAdminDB();
		for (AdminDBItem item: items) {
			if (item.getSettingName().equals(SERVICE_URL)) {
				request.setAttribute(item.getSettingName(), item.getSettingValue());
			}
		}		
		request.setAttribute(UPDATE, "");
		request.setAttribute(SCORE, SCORE);
		request.setAttribute(RESIST, "");
		request.setAttribute(THERAPIST, "");	
		request.setAttribute(TITLE, "Habit Helper Scoreboard");		
		return MY_ACCOUNT_PAGE;

	}
	
	@RequestMapping(value = "/myAccount", method = RequestMethod.GET, params={RESIST})	
	public String showResistButton(HttpServletRequest request) {		
		CookieHandler userCookie = new CookieHandler("user");
		if (!userCookie.checkForCookie(request)) {
			return REDIRECT_LOGIN;
		}		
		if (userCookie.checkForCookie(request)) {
			Cookie c = userCookie.getCookie();
			AuthToken aToken = new AuthToken(c.getValue());
			if (aToken.parseToken()) {
				List<User> ulist = userDao.getUsers();
				Boolean found = false;
				for (User u: ulist) {
					if (u.getId().equals(aToken.getUid())) {				
						request.setAttribute("user", u.getUsername());
						found = true;
					}
				}
				if (!found) {
					return REDIRECT_LOGIN;
				}
			} else {
				return REDIRECT_LOGIN;
			}
		}	
		List<AdminDBItem> items= adminDao.getAdminDB();
		for (AdminDBItem item: items) {
			if (item.getSettingName().equals(SERVICE_URL)) {
				request.setAttribute(item.getSettingName(), item.getSettingValue());
			}
		}		
		request.setAttribute(UPDATE, "");
		request.setAttribute(SCORE, "");
		request.setAttribute(RESIST, RESIST);
		request.setAttribute(THERAPIST, "");		
		request.setAttribute(TITLE, "Habit Helper Resist Button");
		return MY_ACCOUNT_PAGE;

	}

	@RequestMapping(value = "/myAccount", method = RequestMethod.GET, params={THERAPIST})	
	public String showTherapist(HttpServletRequest request) {
		CookieHandler userCookie = new CookieHandler("user");
		if (!userCookie.checkForCookie(request)) {
			return REDIRECT_LOGIN;
		}		
		if (userCookie.checkForCookie(request)) {
			Cookie c = userCookie.getCookie();
			AuthToken aToken = new AuthToken(c.getValue());
			if (aToken.parseToken()) {
				List<User> ulist = userDao.getUsers();
				Boolean found = false;
				for (User u: ulist) {
					if (u.getId().equals(aToken.getUid())) {				
						request.setAttribute("user", u.getUsername());
						found = true;
					}
				}
				if (!found) {
					return REDIRECT_LOGIN;
				}
			} else {
				return REDIRECT_LOGIN;
			}
		}
		List<AdminDBItem> items= adminDao.getAdminDB();
		for (AdminDBItem item: items) {
			if (item.getSettingName().equals(SERVICE_URL)) {
				request.setAttribute(item.getSettingName(), item.getSettingValue());
			}
		}		
		request.setAttribute(UPDATE, "");
		request.setAttribute(SCORE, "");
		request.setAttribute(RESIST, "");
		request.setAttribute(THERAPIST, THERAPIST);
		request.setAttribute(TITLE, "Habit Helper Therapist");		
		return MY_ACCOUNT_PAGE;

	}
	
	@RequestMapping(value = "/myAccount", method = RequestMethod.GET, params={UPDATE})	
	public String showUpdateAccountForm(Model model, HttpServletRequest request) {
		CookieHandler userCookie = new CookieHandler("user");
		if (!userCookie.checkForCookie(request)) {
			return REDIRECT_LOGIN;
		}		
		if (userCookie.checkForCookie(request)) {
			Cookie c = userCookie.getCookie();
			AuthToken aToken = new AuthToken(c.getValue());
			if (aToken.parseToken()) {
				List<User> ulist = userDao.getUsers();
				Boolean found = false;
				for (User u: ulist) {
					if (u.getId().equals(aToken.getUid())) {				
						request.setAttribute("user", u.getUsername());
						request.setAttribute("habit", u.getHabit());
						found = true;
					}
				}
				if (!found) {
					return REDIRECT_LOGIN;
				}
			} else {
				return REDIRECT_LOGIN;
			}
		}
		List<AdminDBItem> items= adminDao.getAdminDB();
		for (AdminDBItem item: items) {
			if (item.getSettingName().equals(SERVICE_URL)) {
				request.setAttribute(item.getSettingName(), item.getSettingValue());
			}
		}
		UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();
		model.addAttribute("updatePasswordForm", updatePasswordForm);
		UpdateHabitForm updateHabitForm = new UpdateHabitForm();
		model.addAttribute("updateHabitForm", updateHabitForm);
		
		request.setAttribute(UPDATE, UPDATE);
		request.setAttribute(SCORE, "");
		request.setAttribute(RESIST, "");
		request.setAttribute(THERAPIST, "");	
		request.setAttribute(TITLE, HABIT_HELPER_ACCOUNT_UPDATE);		
		return MY_ACCOUNT_PAGE;
	}	

	@RequestMapping (value="/myAccount", method=RequestMethod.POST, params={"updatehabit"})
	public String updateHabit(@ModelAttribute("updateHabitForm") UpdateHabitForm updateHabitForm,
		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

		String message = "Unexpected error.  Please Try again. or contact an admin";	
		CookieHandler userCookie = new CookieHandler("user");
		if (!userCookie.checkForCookie(request)) {
			return REDIRECT_LOGIN;
		}		
		if (userCookie.checkForCookie(request)) {
			Cookie c = userCookie.getCookie();
			AuthToken aToken = new AuthToken(c.getValue());
			if (aToken.parseToken()) {
				List<User> ulist = userDao.getUsers();
				Boolean found = false;
				for (User u: ulist) {
					if (u.getId().equals(aToken.getUid())) {
						u.setHabit(updateHabitForm.getHabit());
						userDao.updateUser(u);		
						message = "The habit has been updated!";	
						LogMessage lm = new LogMessage(null, u.getUsername(), request.getHeader("user-agent"), "Habit update successful for user.");	
						logDao.addLog(lm);		
						found = true;
					}
				}
				if (!found) {
					return REDIRECT_LOGIN;
				}
			} else {
				return REDIRECT_LOGIN;
			}			
		}
		List<AdminDBItem> items= adminDao.getAdminDB();
		for (AdminDBItem item: items) {
			if (item.getSettingName().equals(SERVICE_URL)) {
				request.setAttribute(item.getSettingName(), item.getSettingValue());
			}
		}
		
		UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();
		model.addAttribute("updatePasswordForm", updatePasswordForm);		
		request.setAttribute("message", message);
		request.setAttribute(UPDATE, UPDATE);
		request.setAttribute(SCORE, "");
		request.setAttribute(RESIST, "");
		request.setAttribute(THERAPIST, "");		
		request.setAttribute(TITLE, HABIT_HELPER_ACCOUNT_UPDATE);		
		return MY_ACCOUNT_PAGE;	
	}	
	
	
	@RequestMapping (value="/myAccount", method=RequestMethod.POST, params={"updatepassword"})
	public String updatePassword(@ModelAttribute("updatePasswordForm") UpdatePasswordForm updatePasswordForm,
		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		String message = "Unexpected error.  Please Try again. or contact an admin";	
		CookieHandler userCookie = new CookieHandler("user");
		if (!userCookie.checkForCookie(request)) {
			return REDIRECT_LOGIN;
		}		
		if (userCookie.checkForCookie(request)) {
			Cookie c = userCookie.getCookie();
			if (updatePasswordForm.getPassword().equals(updatePasswordForm.getConfirmPassword())) {
				AuthToken aToken = new AuthToken(c.getValue());
				if (aToken.parseToken()) {
					List<User> ulist = userDao.getUsers();
					Boolean found = false;
					for (User u: ulist) {
						if (u.getId().equals(aToken.getUid())) {
							message = "Password update successful!";
							LogMessage lm = new LogMessage(null, u.getUsername(), request.getHeader("user-agent"), "Password update successful for user.");	
							logDao.addLog(lm);
							userDao.updatePassword(u.getUsername(), updatePasswordForm.getPassword());	
							found = true;
						}
					}
					if (!found) {
						return REDIRECT_LOGIN;
					}
				} else {
					return REDIRECT_LOGIN;
				}				
			} else {
				message = "Passwords do not match!";
			}
		}
		List<AdminDBItem> items= adminDao.getAdminDB();
		for (AdminDBItem item: items) {
			if (item.getSettingName().equals(SERVICE_URL)) {
				request.setAttribute(item.getSettingName(), item.getSettingValue());
			}
		}
		
		UpdateHabitForm updateHabitForm = new UpdateHabitForm();
		model.addAttribute("updateHabitForm", updateHabitForm);		
		request.setAttribute("message", message);
		request.setAttribute(UPDATE, UPDATE);
		request.setAttribute(SCORE, "");
		request.setAttribute(RESIST, "");		
		request.setAttribute(THERAPIST, "");
		request.setAttribute(TITLE, HABIT_HELPER_ACCOUNT_UPDATE);		
		return MY_ACCOUNT_PAGE;	
			
	}	

	@RequestMapping(value = "/createLocalUrlFile/{username:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String createLocalUrlFile(HttpServletResponse response, HttpServletRequest request, @PathVariable("username") String username) {

		String serviceUrl = "";
		List<AdminDBItem> items= adminDao.getAdminDB();
		for (AdminDBItem item: items) {
			if (item.getSettingName().equals(SERVICE_URL)) {
				serviceUrl = item.getSettingValue();
			}
		}
		String urlFileLocationUrl = "/etc/hh_url.txt";
		String urlFileLocationUser = "/etc/hh_user.txt";
		
		Path path1 = Paths.get(urlFileLocationUrl);
		Path path2= Paths.get(urlFileLocationUser);

		try {
			Files.write(path1, serviceUrl.getBytes());
			Files.write(path2,  username.getBytes());
		} catch (IOException e) {
			return "{\"Update\":\"Failed\"}";
		}

		return "{\"Update\":\"Success\"}";

	}			
		
}