package com.securitymagick.domain;

import java.util.Base64;

/**
 * @author leggosgirl
 *
 */
public class Permissions {
	private Boolean user=false;
	private Boolean admin=false;

	
	/**
	 * 
	 */
	public Permissions() {
		super();
		user= false;
		admin = false;
	}


	/**
	 * @param user
	 * @param admin
	 */
	public Permissions(Boolean user, Boolean admin) {
		super();
		this.user = user;
		this.admin = admin;
	}
	
	/**
	 * @param cookieValue
	 */
	public Permissions(String cookieValue) {
		this();
		String value = new String(Base64.getDecoder().decode(cookieValue.getBytes()));
		if (value.equals("user")) {
			user =true;
		}
		if (value.equals("admin")) {
			admin = true;
		}
		if (value.equals("user,admin")) {
			user = true;
			admin = true;
		}
	}


	/**
	 * @return the user
	 */
	public Boolean getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(Boolean user) {
		this.user = user;
	}


	/**
	 * @return the admin
	 */
	public Boolean getAdmin() {
		return admin;
	}


	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getCookieValue() {
		String value = "none";
		if (user && admin) {
			value = "user,admin";
		} else if (user) {
			value = "user";
		} else if (admin) {
			value = "admin";
		}
		return Base64.getEncoder().encodeToString(value.getBytes());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Permissions [user=" + user + ", admin=" + admin + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((admin == null) ? 0 : admin.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}


	/* (non-Javadoc)
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
		Permissions other = (Permissions) obj;
		if (admin == null) {
			if (other.admin != null)
				return false;
		} else if (!admin.equals(other.admin))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	
}
