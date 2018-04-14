package com.securitymagick.web.cookie;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

/**
 * @author leggosgirl
 *
 */
public class CookieHandler {
	private String cookieName=null;
	private Cookie cookie = null;

	/**
	 * @param cookieName
	 */
	public CookieHandler(String cookieName) {
		super();
		this.cookieName = cookieName;
	}
	
	public Boolean checkForCookie(HttpServletRequest request) {
		Boolean found = false;
		Cookie[] requestCookies = request.getCookies();
		if(requestCookies != null){
			for(Cookie c : requestCookies){
				if (c.getName().equals(cookieName)) {
					found = true;
					cookie = c;
				}
			}
		}
		return found;
	}

	public void addCookie(HttpServletResponse response, String value) {
		cookie = new Cookie(cookieName, value);
		cookie.setPath("/habit-helper-1.0");
		response.addCookie(cookie);
	}
	
	public Cookie getCookie() {
		return cookie;
	}
}