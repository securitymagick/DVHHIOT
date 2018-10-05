package com.securitymagick.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.securitymagick.domain.ForgotPasswordToken;

@Component("tokenChecker")
public class ForgotPasswordTokenChecker {
	private List<ForgotPasswordToken> forgotPasswordTokens = new ArrayList<ForgotPasswordToken>();
	String helper = "";
	
	/**
	 * @return the helper
	 */
	public final String getHelper() {
		return helper;
	}

	public void addToken(ForgotPasswordToken token) {
		forgotPasswordTokens.add(token);
	}
	
	public boolean removeToken(ForgotPasswordToken token) {
		return forgotPasswordTokens.remove(token);
	}
	
	public Boolean isTokenValid(ForgotPasswordToken token, String user) {	
		if (!token.getUser().equals(user)) {
			helper = "failed user match";
			return false;
		}
		if (token.isExpired()) {
			helper = "failed expiration";
			return false;
		}
		if (!forgotPasswordTokens.contains(token)) {
			helper="failed contains";
			return false;
		}		
		helper = "passed all";
		return true;
	}
}
