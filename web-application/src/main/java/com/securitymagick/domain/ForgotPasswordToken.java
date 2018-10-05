package com.securitymagick.domain;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ForgotPasswordToken {
	String token;
	String user;
	NumericDate expires;
	String helper = "";

	/**
	 * @param token
	 * @param user
	 * @param expires
	 */
	public ForgotPasswordToken(String token, String user, long expiresInSeconds) {
		this.token = token;
		this.user = user;
		this.expires = NumericDate.now();
		this.expires.addSeconds(expiresInSeconds);
	}
	
	public ForgotPasswordToken(String fullTokenString) {
		helper = "";
		String tokenString = fullTokenString;
		tokenString = new String(Base64.getDecoder().decode(fullTokenString.getBytes()));
		
		String tokenSubString = tokenString.substring(1, tokenString.length()-1);
		helper+="substring" + tokenSubString + ":";
		List<String> valueList = Arrays.asList(tokenSubString.split(","));
		for (String value : valueList) {
			helper += "(" + value + ")";
			String[] item = value.split(":");
			if (item[0].equals("token")) {
				this.token = item[1];
				helper += ":token:"+ item[1];
			}
			if (item[0].equals("user")) {
				this.user = item[1];
				helper += ":user:"+ item[1];
			}
			if (item[0].equals("expires")) {
				this.expires = new NumericDate(Long.parseLong(item[1]));
				helper += ":expires:" + item[1];
			}
		}
	}

	public Boolean isExpired() {
		NumericDate current = NumericDate.now();
		return current.isAfter(expires);
	}
	
	/**
	 * @return the helper
	 */
	public final String getHelper() {
		return helper;
	}

	/**
	 * @return the token
	 */
	public final String getToken() {
		return token;
	}



	/**
	 * @param token the token to set
	 */
	public final void setToken(String token) {
		this.token = token;
	}



	/**
	 * @return the expires
	 */
	public final NumericDate getExpires() {
		return expires;
	}



	/**
	 * @param expires the expires to set
	 */
	public final void setExpires(NumericDate expires) {
		this.expires = expires;
	}



	/**
	 * @return the user
	 */
	public final String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public final void setUser(String user) {
		this.user = user;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expires == null) ? 0 : expires.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ForgotPasswordToken)) {
			return false;
		}
		ForgotPasswordToken other = (ForgotPasswordToken) obj;
		if (expires == null) {
			if (other.expires != null) {
				return false;
			}
		} else if (!expires.equals(other.expires)) {
			return false;
		}
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		} else if (!token.equals(other.token)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String tokenString = "[token:" + token + ",user:" + user + ",expires:" + expires.toString() + "]";
		return Base64.getEncoder().encodeToString(tokenString.getBytes());
	}

	
	
}
