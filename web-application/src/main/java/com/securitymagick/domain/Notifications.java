/**
 * 
 */
package com.securitymagick.domain;

import java.util.Base64;

/**
 * @author NTISNS01
 *
 */
public class Notifications {
	private Boolean post=false;
	private Boolean comment=false;
	
	/**
	 * Default constructor
	 */
	public Notifications() {
		post=false;
		comment=false;
	}
	
	/**
	 * Constructor
	 * @param post
	 * @param comment
	 */
	public Notifications(Boolean post, Boolean comment) {
		this.post = post;
		this.comment = comment;
	}

	/**
	 * Constructor
	 * @param cookieValue
	 */
	public Notifications(String cookieValue) {
		this();
		String value = new String(Base64.getDecoder().decode(cookieValue.getBytes()));
		if (value.equals("post")) {
			post =true;
		}
		if (value.equals("comment")) {
			comment = true;
		}
		if (value.equals("post,comment")) {
			post = true;
			comment = true;
		}
	}
	
	
	/**
	 * @return the post
	 */
	public final Boolean getPost() {
		return post;
	}


	/**
	 * @param post the post to set
	 */
	public final void setPost(Boolean post) {
		this.post = post;
	}


	/**
	 * @return the comment
	 */
	public final Boolean getComment() {
		return comment;
	}


	/**
	 * @param comment the comment to set
	 */
	public final void setComment(Boolean comment) {
		this.comment = comment;
	}


	public String getCookieValue() {
		String value = "empty";
		if (post && comment) {
			value = "post,comment";
		} else if (post) {
			value = "post";
		} else if (comment) {
			value = "comment";
		}
		return Base64.getEncoder().encodeToString(value.getBytes());
	}	
	
	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Notifications [post=" + post + ", comment=" + comment + "]";
	}
	
}
