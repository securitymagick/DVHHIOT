package com.securitymagick.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.securitymagick.web.cookie.CookieHandler;

@Component("csrfTokenChecker")
public class CSRFTokenChecker {
	public static String CSRF_COOKIE_NAME = "CSRFTOKEN";
	
	/**
	 * Default Constructor
	 */
	public CSRFTokenChecker() {
		
	}
	
	public Boolean isValid(String csrfTokenValue, HttpServletRequest request) {
		if (csrfTokenValue.isEmpty()) {
			return false;
		}
		CookieHandler csrfCookie = new CookieHandler(CSRF_COOKIE_NAME);
		if (csrfCookie.checkForCookie(request)) {
			if (!csrfTokenValue.equals(csrfCookie.getCookie().getValue())) {
				return false;
			}
		} else {
		   return false;
	   }
		
		return true;
	}
}
