package com.securitymagick.domain;

/**
 * @author leggosgirl
 *
 */
public class User {
	private Integer id;
	private String username;
	private String password;
	private String question;
	private String answer;
	private String habit;
	private Integer isUser;
	private Integer isAdmin;
	private String csrfToken = "";
	
	public User() {
		super();
	}
	
	
	public User(String username, String password, Integer id, String habit, Integer isUser, Integer isAdmin) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
		this.habit = habit;
		this.isUser = isUser;
		this.isAdmin = isAdmin;
	}
	
	
	
	/**
	 * @param id
	 * @param username
	 * @param password
	 * @param question
	 * @param answer
	 * @param habit
	 * @param isUser
	 * @param isAdmin
	 * @param csrfToken
	 */
	public User(Integer id, String username, String password, String question, String answer, String habit,
			Integer isUser, Integer isAdmin, String csrfToken) {
		this(username, password, id, habit, isUser, isAdmin);
		this.question = question;
		this.answer = answer;
		this.csrfToken = csrfToken;
	}


	/**
	 * @return the csrfToken
	 */
	public final String getCsrfToken() {
		return csrfToken;
	}


	/**
	 * @param csrfToken the csrfToken to set
	 */
	public final void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
		public Integer getIsUser() {
		return isUser;
	}
	public void setIsUser(Integer isUser) {
		if (isUser > 0 ) {
			this.isUser = 1;
		} else {
			this.isUser = 0;
		}
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		if (isAdmin > 0) {
			this.isAdmin = 1;
		} else {
			this.isAdmin = 0;
		}
	}
	
	public String getHabit() {
		return habit;
	}
	public void setHabit(String habit) {
		this.habit = habit;
	}

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}	

	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}	
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", id="
				+ id.toString() + ", habit=" + habit + ", isUser=" + isUser + ", isAdmin=" + isAdmin + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((habit == null) ? 0 : habit.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((isUser == null) ? 0 : isUser.hashCode());
		result = prime * result + ((isAdmin == null) ? 0 : isAdmin.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (habit == null) {
			if (other.habit != null)
				return false;
		} else if (!habit.equals(other.habit))
			return false;
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
		if (isUser == null) {
			if (other.isUser != null)
				return false;
		} else if (!isUser.equals(other.isUser))
			return false;
		if (isAdmin == null) {
			if (other.isAdmin != null)
				return false;
		} else if (!isAdmin.equals(other.isAdmin))
			return false;
		return true;
	}
	
	
	
}
