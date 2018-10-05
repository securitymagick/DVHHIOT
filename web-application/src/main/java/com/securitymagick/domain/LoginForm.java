package com.securitymagick.domain;

/**
 * @author Harry
 *
 */
public class LoginForm {
	private String username;
	private String password;
	private boolean rememberme=false;
	private String rememberMeToken = "";
	
	/**
	 * 
	 */
	public LoginForm() {
		super();
	}

	/**
	 * @param username
	 * @param password
	 */
	public LoginForm(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	
	
	/**
	 * @param username
	 * @param password
	 * @param rememberme
	 * @param rememberMeToken
	 */
	public LoginForm(String username, String password, boolean rememberme, String rememberMeToken) {
		this.username = username;
		this.password = password;
		this.rememberme = rememberme;
		this.rememberMeToken = rememberMeToken;
	}

	
	/**
	 * @return the rememberme
	 */
	public final boolean isRememberme() {
		return rememberme;
	}

	/**
	 * @param rememberme the rememberme to set
	 */
	public final void setRememberme(boolean rememberme) {
		this.rememberme = rememberme;
	}

	/**
	 * @return the rememberMeToken
	 */
	public final String getRememberMeToken() {
		return rememberMeToken;
	}

	/**
	 * @param rememberMeToken the rememberMeToken to set
	 */
	public final void setRememberMeToken(String rememberMeToken) {
		this.rememberMeToken = rememberMeToken;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginForm [username=" + username + ", password=" + password + "]";
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginForm other = (LoginForm) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}