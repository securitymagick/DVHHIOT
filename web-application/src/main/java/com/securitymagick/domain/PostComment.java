package com.securitymagick.domain;


/**
 * @author leggosgirl
 *
 */
public class PostComment {
	private Integer id;
	private String comment;
	private String postid;
	private String username;
	private String csrfToken = "";
	
	/**
	 * 
	 */
	public PostComment() {
	}

	/**
	 * @param id
	 * @param comment
	 * @param postid
	 * @param username
	 */
	public PostComment(Integer id, String comment, String postid, String username) {
		this.id = id;
		this.comment = comment;
		this.postid = postid;
		this.username = username;
	}
	
	

	/**
	 * @param id
	 * @param comment
	 * @param postid
	 * @param username
	 * @param csrfToken
	 */
	public PostComment(Integer id, String comment, String postid, String username, String csrfToken) {
		this.id = id;
		this.comment = comment;
		this.postid = postid;
		this.username = username;
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

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	 * @return the postid
	 */
	public String getPostid() {
		return postid;
	}

	/**
	 * @param postid the postid to set
	 */
	public void setPostid(String postid) {
		this.postid = postid;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PostComment [id =" + id.toString() + ", comment=" + comment + ", postid=" + postid
				+ ", username=" + username + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((postid == null) ? 0 : postid.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		PostComment other = (PostComment) obj;
		if (comment == null) {
			if (other.comment != null) {
				return false;
			}
		} else if (!comment.equals(other.comment)) {
			return false;
		}
		if (postid == null) {
			if (other.postid != null) {
				return false;
			}
		} else if (!postid.equals(other.postid)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}		
		return true;
	}
	
}