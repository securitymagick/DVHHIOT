package com.securitymagick.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.securitymagick.domain.AdminDBItem;
import com.securitymagick.domain.LogMessage;
import com.securitymagick.domain.Post;
import com.securitymagick.domain.PostComment;
import com.securitymagick.domain.User;
import com.securitymagick.domain.dao.AdminDao;
import com.securitymagick.domain.dao.LogDao;
import com.securitymagick.domain.dao.PostDao;
import com.securitymagick.domain.dao.UserDao;
import com.securitymagick.service.CSRFTokenChecker;

@Controller
public class AdminController {
	private static final String ADMIN_SETTINGS = "adminSettings";

	private static final String MESSAGE_ATTRIBUTE_NAME = "message";

	private static final String POSTS_ATTRIBUTE_NAME = "posts";
	
	private static final String ADMIN_ATTRIBUTE_NAME = "adminItems";

	private static final String USERS_ATTRIBUTE_NAME = "users";

	private static final String COMMENTS_ATTRIBUTE_NAME = "comments";

	private static final String LOGS_ATTRIBUTE_NAME = "logs";

	private static final String SHOW_EDIT_POST = "showEditPost";

	private static final String SHOW_DELETE_POST = "showDeletePost";

	private static final String SHOW_EDIT_ACCOUNT = "showEditAccount";

	private static final String SHOW_DELETE_ACCOUNT = "showDeleteAccount";

	private static final String SHOW_ACCOUNTS = "showAccounts";

	private static final String SHOW_POSTS = "showPosts";

	private static final String SHOW_COMMENTS = "showComments";

	private static final String SHOW_LOGS = "showLogs";

	private static final String ADMIN_PAGE = "admin";

	@Autowired
	LogDao logDao;
	
	@Autowired
	PostDao postDao;	

	@Autowired
	UserDao userDao;	

	@Autowired
	AdminDao adminDao;	
	
	@Autowired
	CSRFTokenChecker csrfTokenChecker;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String showAdmin(HttpServletRequest request) {
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, "");
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");	
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");	
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;

	}

	
	@RequestMapping(value = "/admin", method = RequestMethod.GET,  params={LOGS_ATTRIBUTE_NAME})
	public String showLogs(HttpServletRequest request) {
		List<LogMessage> logs= logDao.getLogMessages();
		request.setAttribute(LOGS_ATTRIBUTE_NAME, logs);
		request.setAttribute(SHOW_LOGS, SHOW_LOGS);
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, "");
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");	
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;
	}	

	@RequestMapping(value = "/admin", method = RequestMethod.GET, params={COMMENTS_ATTRIBUTE_NAME})
	public String showComments(HttpServletRequest request) {
		List<PostComment> comments= postDao.getComments();
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, SHOW_COMMENTS);
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, "");	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");	
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");			
		request.setAttribute(COMMENTS_ATTRIBUTE_NAME, comments);
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET, params={"deleteComment"})
	public String deleteComment(HttpServletRequest request, @RequestParam("deleteComment") String id) {
		postDao.deleteComment(id);
		List<PostComment> comments= postDao.getComments();
		request.setAttribute(COMMENTS_ATTRIBUTE_NAME, comments);
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, SHOW_COMMENTS);
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, "");
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");	
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");	
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET, params={"accounts"})
	public String showAccounts(HttpServletRequest request) {
		List<User> users= userDao.getUsers();
		request.setAttribute(USERS_ATTRIBUTE_NAME, users);
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, SHOW_ACCOUNTS);	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");	
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");	
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");	
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;

	}	
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET, params={POSTS_ATTRIBUTE_NAME})
	public String showPost(HttpServletRequest request) {
		List<Post> posts= postDao.getPosts();
		request.setAttribute(POSTS_ATTRIBUTE_NAME, posts);
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, SHOW_POSTS);	
		request.setAttribute(SHOW_ACCOUNTS, "");	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");	
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");	
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;

	}	
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET, params={ADMIN_SETTINGS})
	public String showAdminSettings(HttpServletRequest request, Model model) {
		AdminDBItem adminDBItem = new AdminDBItem();
		model.addAttribute("adminDBItemToEdit", adminDBItem);
		List<AdminDBItem> items= adminDao.getAdminDB();
		request.setAttribute(ADMIN_ATTRIBUTE_NAME, items);
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, "");	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");	
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");	
		request.setAttribute(ADMIN_SETTINGS, ADMIN_SETTINGS);
		return ADMIN_PAGE;

	}	

	@RequestMapping(value = "/admin", method = RequestMethod.GET, params={"deleteaccount"})
	public String showDeleteAccount(HttpServletRequest request, HttpServletResponse response, @RequestParam("deleteaccount") String id, Model model) {
		User userToDelete = new User();
		model.addAttribute("userToDelete", userToDelete);
		List<User> users= userDao.getUsers();
		Integer userid = new Integer(id);

		for (User u1 : users) {
			if (u1.getId().equals(userid)) {
				request.setAttribute("user", u1);
			} 
		}
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, "");
		request.setAttribute(SHOW_DELETE_ACCOUNT, SHOW_DELETE_ACCOUNT);		
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");	
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;

	}

	@RequestMapping(value = "/admin", method = RequestMethod.POST, params={"deletetheaccount"})
	public String deleteAccount(@ModelAttribute("user") User userToDelete,
		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		String message = "An unexpected error occurred.  You may need to logout and log back in.";
		if (csrfTokenChecker.isValid(userToDelete.getCsrfToken(), request)) {		
			userDao.deleteUser(userToDelete.getId());	
			message = "The user has been deleted!";
		}
		request.setAttribute(MESSAGE_ATTRIBUTE_NAME, message);
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, SHOW_ACCOUNTS);	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");	
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");		
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");			
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;
	}	
	
	@RequestMapping(value = "/updateServiceUrl", method = RequestMethod.GET, params={"url"}, produces = "application/json")
	public @ResponseBody String updateServiceUrl(HttpServletResponse response, HttpServletRequest request, @RequestParam(value="url") String url) {

		Integer id = -1;
		List<AdminDBItem> items= adminDao.getAdminDB();
		for (AdminDBItem item: items) {
			if (item.getSettingName().equals("serviceURL")) {
				id = item.getId();
			}
		}
		if (id > 0) {
			AdminDBItem adminDBItemToEdit = new AdminDBItem(id, "serviceURL", url);
			adminDao.updateAdminDBItem(adminDBItemToEdit);
		
			return "{\"Update\":\"Success\"}";
		} else {
			return "{\"Update\":\"Failed\"}";
		}
	}	
	
	@RequestMapping(value = "/admin", method = RequestMethod.POST, params={"editadminitem"})
	public String editAdminItem(@ModelAttribute("adminDBItemToEdit") AdminDBItem adminDBItemToEdit,
		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		adminDao.updateAdminDBItem(adminDBItemToEdit);
		String message = "The " + adminDBItemToEdit.getSettingName() +" admin config option has been edited!";
		List<AdminDBItem> items= adminDao.getAdminDB();
		request.setAttribute(ADMIN_ATTRIBUTE_NAME, items);
		request.setAttribute(MESSAGE_ATTRIBUTE_NAME, message);
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, "");	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");	
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");		
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");			
		request.setAttribute(ADMIN_SETTINGS, ADMIN_SETTINGS);
		return ADMIN_PAGE;
	}	
	

	@RequestMapping(value = "/admin", method = RequestMethod.GET, params={"editaccount"})
	public String showEditAccount(HttpServletRequest request, HttpServletResponse response, @RequestParam("editaccount") String id, Model model) {
		User userToEdit = new User();
		model.addAttribute("userToEdit", userToEdit);
		List<User> users= userDao.getUsers();
		Integer userid = new Integer(id);

		for (User u1 : users) {
			if (u1.getId().equals(userid)) {
				request.setAttribute("user", u1);
			} 
		}
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, "");	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");	
		request.setAttribute(SHOW_EDIT_ACCOUNT, SHOW_EDIT_ACCOUNT);	
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;

	}

	@RequestMapping(value = "/admin", method = RequestMethod.POST, params={"edittheaccount"})
	public String editAccount(@ModelAttribute("userToEdit") User userToEdit,
		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		String message = "An unexpected error occurred.  You may need to logout and log back in.";
		if (csrfTokenChecker.isValid(userToEdit.getCsrfToken(), request)) {		
			userDao.updateUser(userToEdit);	
			message = "The account has been modified!";
		}
		request.setAttribute(MESSAGE_ATTRIBUTE_NAME, message);
		request.setAttribute(SHOW_LOGS, "");
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, SHOW_ACCOUNTS);	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");		
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");	
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;
	}	
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET, params={"deletepost"})
	public String showDeletePost(HttpServletRequest request, HttpServletResponse response, @RequestParam("deletepost") String id, Model model) {
		Post postToDelete = new Post();
		model.addAttribute("postToDelete", postToDelete);
		List<Post> posts= postDao.getPosts();
		Integer postid = new Integer(id);

		for (Post p1 : posts) {
			if (p1.getId().equals(postid)) {
				request.setAttribute("post", p1);
			} 
		}
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, "");	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");		
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");
		request.setAttribute(SHOW_DELETE_POST, SHOW_DELETE_POST);		
		request.setAttribute(SHOW_EDIT_POST, "");
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;

	}

	@RequestMapping(value = "/admin", method = RequestMethod.POST, params={"deletepost"})
	public String deletePost(@ModelAttribute("post") Post postToDelete,
		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		String message = "An unexpected error occurred.  You may need to logout and log back in.";
		if (csrfTokenChecker.isValid(postToDelete.getCsrfToken(), request)) {		
			postDao.deletePost(postToDelete.getId());	
			message = "The post has been deleted!";
		}
		request.setAttribute(MESSAGE_ATTRIBUTE_NAME, message);
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, SHOW_POSTS);	
		request.setAttribute(SHOW_ACCOUNTS, "");	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");		
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET, params={"editpost"})
	public String showEditPost(HttpServletRequest request, HttpServletResponse response, @RequestParam("editpost") String id, Model model) {
		Post postToEdit = new Post();
		model.addAttribute("postToEdit", postToEdit);
		List<Post> posts= postDao.getPosts();
		Integer postid = new Integer(id);

		for (Post p1 : posts) {
			if (p1.getId().equals(postid)) {
				request.setAttribute("post", p1);
			} 
		}
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, "");	
		request.setAttribute(SHOW_ACCOUNTS, "");	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");		
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, SHOW_EDIT_POST);			
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;

	}

	@RequestMapping(value = "/admin", method = RequestMethod.POST, params={"editpost"})
	public String editPost(@ModelAttribute("postToEdit") Post postToEdit,
		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		String message = "An unexpected error occurred.  You may need to logout and log back in.";
		if (csrfTokenChecker.isValid(postToEdit.getCsrfToken(), request)) {		
			postDao.updatePost(postToEdit);	
			message = "The post has been modified!";
		}
		request.setAttribute(MESSAGE_ATTRIBUTE_NAME, message);
		request.setAttribute(SHOW_COMMENTS, "");
		request.setAttribute(SHOW_POSTS, SHOW_POSTS);	
		request.setAttribute(SHOW_ACCOUNTS, "");	
		request.setAttribute(SHOW_DELETE_ACCOUNT, "");		
		request.setAttribute(SHOW_EDIT_ACCOUNT, "");
		request.setAttribute(SHOW_DELETE_POST, "");	
		request.setAttribute(SHOW_EDIT_POST, "");
		request.setAttribute(ADMIN_SETTINGS, "");
		return ADMIN_PAGE;
	}	
}