package com.securitymagick.domain;

/**
 * @author Bob
 *
 */
public class ForumPostForm {

	private String title;
	private String author;
	private String imageName;
	private String thePost;
	private String csrfToken = "";
	
	public ForumPostForm() {
		super();
	}
	
	
	public ForumPostForm(String title, String author, String imageName, String thePost) {
		super();
		this.title = title;
		this.author = author;
		this.imageName = imageName;
		this.thePost = thePost;
	}
	
	
	
	/**
	 * @param title
	 * @param author
	 * @param imageName
	 * @param thePost
	 * @param csrfToken
	 */
	public ForumPostForm(String title, String author, String imageName, String thePost, String csrfToken) {
		this.title = title;
		this.author = author;
		this.imageName = imageName;
		this.thePost = thePost;
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


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getThePost() {
		return thePost;
	}
	public void setThePost(String thePost) {
		this.thePost = thePost;
	}	
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "ForumPostForm [" + "title=" + title + ", author=" + author + "imageName=" + imageName + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result + ((thePost == null) ? 0 : thePost.hashCode());		
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
		ForumPostForm other = (ForumPostForm) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (thePost == null) {
			if (other.thePost != null)
				return false;
		} else if (!thePost.equals(other.thePost))
			return false;		
		if (imageName == null) {
			if (other.imageName != null)
				return false;
		} else if (!imageName.equals(other.imageName))
			return false;		
		return true;
	}
	
	
	
}
