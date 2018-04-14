/**
 * 
 */
package com.securitymagick.web.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.securitymagick.domain.Notifications;

/**
 * @author Harry
 *
 */
public class NotificationCookie {
	private static final String NOTIFICATIONS_COOKIE_NAME = "notifications";
	private Cookie notifyCookie = null;


	public NotificationCookie() {
		super();
	}
	
	public Boolean checkForCookie(HttpServletRequest request) {
		Boolean found = false;
		Cookie[] requestCookies = request.getCookies();
		if(requestCookies != null){
			for(Cookie c : requestCookies){
				if (c.getName().equals(NOTIFICATIONS_COOKIE_NAME)) {
					found = true;
					notifyCookie = c;
				}
			}
		}
		return found;
	}

	public void addCookie(HttpServletResponse response, Notifications notifications) {
		notifyCookie = new Cookie(NOTIFICATIONS_COOKIE_NAME, notifications.getCookieValue());
		response.addCookie(notifyCookie);
	}
	
	public Cookie getNotificationsCookie() {
		return notifyCookie;
	}

}
