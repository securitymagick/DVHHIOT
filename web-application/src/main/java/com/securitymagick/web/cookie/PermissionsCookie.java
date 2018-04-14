package com.securitymagick.web.cookie;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

import com.securitymagick.domain.Permissions;
/**
 * @author Harry
 *
 */
public class PermissionsCookie {
	private static final String PERMISSIONS_COOKIE_NAME = "permissions";
	private Cookie permsCookie = null;

	/**
	 * 
	 */
	public PermissionsCookie() {
		super();
	}
	
	public Boolean checkForCookie(HttpServletRequest request) {
		Boolean found = false;
		Cookie[] requestCookies = request.getCookies();
		if(requestCookies != null){
			for(Cookie c : requestCookies){
				if (c.getName().equals(PERMISSIONS_COOKIE_NAME)) {
					found = true;
					permsCookie = c;
				}
			}
		}
		return found;
	}

	public void addCookie(HttpServletResponse response, Permissions permissions) {
		permsCookie = new Cookie(PERMISSIONS_COOKIE_NAME, permissions.getCookieValue());
		response.addCookie(permsCookie);
	}
	
	public Cookie getPermissionsCookie() {
		return permsCookie;
	}
}