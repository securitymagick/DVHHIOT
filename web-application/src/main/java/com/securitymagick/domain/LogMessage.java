package com.securitymagick.domain;



/**
 * @message leggosgirl
 *
 */
public class LogMessage {
	private Integer id = null;
	private String username = null;
	private String useragent= null;
	private String message= null;

	/**
	 * 
	 */
	public LogMessage() {
	}

	/**
	 * @param id
	 * @param username
	 * @param useragent
	 * @param message
	 */
	public LogMessage(Integer id, String username, String useragent, String message) {
		this.id = id;
		this.username = username;
		this.useragent = useragent;
		this.message = message;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the useragent
	 */
	public String getUseragent() {
		return useragent;
	}

	/**
	 * @param useragent the useragent to set
	 */
	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LogMessage [id=" + id + ", username=" + username + ", useragent="
				+ useragent + ", message=" + message + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((useragent == null) ? 0 : useragent.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		LogMessage other = (LogMessage) obj;
		
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (useragent == null) {
			if (other.useragent != null) {
				return false;
			}
		} else if (!useragent.equals(other.useragent)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	
}
