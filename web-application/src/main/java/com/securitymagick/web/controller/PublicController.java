package com.securitymagick.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.Cookie;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.securitymagick.domain.Permissions;
import com.securitymagick.domain.Post;
import com.securitymagick.domain.PostComment;
import com.securitymagick.domain.User;
import com.securitymagick.domain.AdminDBItem;
import com.securitymagick.domain.AuthToken;
import com.securitymagick.domain.ForumPostForm;
import com.securitymagick.domain.Notifications;
import com.securitymagick.domain.dao.AdminDao;
import com.securitymagick.domain.dao.PostDao;
import com.securitymagick.domain.dao.UserDao;
import com.securitymagick.service.CSRFTokenChecker;
import com.securitymagick.web.cookie.CookieHandler;
import com.securitymagick.web.cookie.NotificationCookie;
import com.securitymagick.web.cookie.PermissionsCookie;


@Controller
public class PublicController {
	private static final String PUBLIC_PAGE = "public";


	private static final String HABIT_HELPER_FORUMS = "Habit Helper Forums";


	private static final String TITLE_ATTRIBUTE_NAME = "title";


	private static final String PART2 = "part2";


	private static final String PART1 = "part1";


	private static final String READ = "read";


	private static final String WRITE = "write";


	private static final String FORUM = "forum";


	public static final String ROOT_LINUX = "/var/lib/tomcat8/webapps/habit-helper-1.0/resources/core/images";
	public static final String ROOT_WINDOWS = "C:\\Users\\NTISNS01\\Desktop\\etc\\Demo\\Tools\\apache-tomcat-8.0.33\\webapps\\habit-helper-1.0\\resources\\core\\images";


	@Autowired
	PostDao postDao;
	
	@Autowired
	AdminDao adminDao;	
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CSRFTokenChecker csrfTokenChecker;

	@RequestMapping(value = "/public", method = RequestMethod.GET)
	public ModelAndView showPublic(HttpServletRequest request, HttpServletResponse response) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}		
		request.setAttribute(FORUM, "");
		request.setAttribute(WRITE, "");
		request.setAttribute(READ, "");
		request.setAttribute(PART1, "");
		request.setAttribute(PART2, "");
		request.setAttribute(TITLE_ATTRIBUTE_NAME, HABIT_HELPER_FORUMS);			
	
		ModelAndView model = new ModelAndView();
		model.setViewName(PUBLIC_PAGE);

		return model;

	}
	
	@RequestMapping(value = "/public", method = RequestMethod.GET, params={FORUM})
	public ModelAndView showPostsList(HttpServletRequest request, HttpServletResponse response) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}	
		request.setAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper Forums -- Read Posts");			
		request.setAttribute(FORUM, FORUM);
		request.setAttribute(WRITE, "");
		request.setAttribute(READ, "");
		request.setAttribute(PART1, "");
		request.setAttribute(PART2, "");
	
		List<Post> posts= postDao.getPosts();
		request.setAttribute("posts", posts);
		ModelAndView model = new ModelAndView();
		model.setViewName(PUBLIC_PAGE);

		return model;

	}	

	@RequestMapping(value = "/public", method = RequestMethod.GET, params={WRITE})
	public ModelAndView addForumPost(HttpServletRequest request, HttpServletResponse response) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}	
		request.setAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper Forums -- Write a New Post");		
		request.setAttribute(FORUM, "");
		request.setAttribute(WRITE, WRITE);
		request.setAttribute(READ, "");
		request.setAttribute(PART1, PART1);
		request.setAttribute(PART2, "");		

		ModelAndView model = new ModelAndView();
		model.setViewName(PUBLIC_PAGE);

		return model;
	}	

	@RequestMapping(value = "/public", method = RequestMethod.POST, params={"uploadfile"})
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
								    Model model, HttpServletRequest request, HttpServletResponse response) {
		PermissionsCookie pCookie = new PermissionsCookie();
		String root="C:\\";
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}										
		CookieHandler userCookie = new CookieHandler("user");
		if (userCookie.checkForCookie(request)) {
			Cookie c = userCookie.getCookie();
			AuthToken aToken = new AuthToken(c.getValue());
			if (aToken.parseToken()) {
				List<User> ulist = userDao.getUsers();
				for (User u: ulist) {
					if (u.getId().equals(aToken.getUid())) {				
						request.setAttribute("user", u.getUsername());
					}
				}
			}
		}	
		List<AdminDBItem> items= adminDao.getAdminDB();
		for (AdminDBItem item: items) {
			if (item.getSettingName().equals("uploadDirectory")) {
				root = item.getSettingValue();
			}
		}		
		String message = "Unable to upload file.";
		request.setAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper Forums -- Write a New Post");			
		request.setAttribute(FORUM, "");
		request.setAttribute(WRITE, WRITE);
		request.setAttribute(READ, "");				
		if (!file.isEmpty()) {
			try {
				Files.copy(file.getInputStream(), Paths.get(root, file.getOriginalFilename()));
				request.setAttribute("imageName", file.getOriginalFilename());
				request.setAttribute(PART1, "");
				request.setAttribute(PART2, PART2);
				ForumPostForm forumPostForm = new ForumPostForm();
				model.addAttribute("forumPostForm", forumPostForm);
				message = "File uploaded successfully";
			}  catch (IOException|RuntimeException e) {
				message = "Exception:" + e.toString();
			}
		}
		request.setAttribute("message", message);
		return PUBLIC_PAGE;
	}
	
	@RequestMapping(value = "/public", method = RequestMethod.POST, params={"createpost"}) 
	public String addPost(@ModelAttribute("forumPostForm") ForumPostForm forumPostForm,
		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		PermissionsCookie pCookie = new PermissionsCookie();
		if (!pCookie.checkForCookie(request)) {
			Permissions p = new Permissions();
			pCookie.addCookie(response, p);
		}			
		request.setAttribute(FORUM, "");
		request.setAttribute(WRITE, "");
		request.setAttribute(READ, "");					
		request.setAttribute(PART1, "");
		request.setAttribute(PART2, "");
		request.setAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper Forums -- Create your post");			
		
		String message = "There is a problem with your request.  You may need to logout and login again to continue.";
		if (csrfTokenChecker.isValid(forumPostForm.getCsrfToken(), request)) {		
			Post p = new Post(null, forumPostForm.getTitle(), forumPostForm.getImageName(), forumPostForm.getAuthor(), forumPostForm.getThePost() ,null);

			postDao.addPost(p);
			message = "Your post has been added.";
		
			NotificationCookie nCookie = new NotificationCookie();
			Notifications n;
			if (!nCookie.checkForCookie(request)) {
				n = new Notifications();
			} else {
				n = new Notifications(nCookie.getNotificationsCookie().getValue());
			}
			n.setPost(true);
			nCookie.addCookie(response, n);
		}
		request.setAttribute("message", message);
		return PUBLIC_PAGE;		
	}
	
	@RequestMapping(value = "/public", method = RequestMethod.GET, params={READ})
	public String showPost(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id, Model model) {

		PostComment postComment = new PostComment();
		model.addAttribute("postComment", postComment);
		List<Post> posts= postDao.getPostsWithComments();
		Integer postid = new Integer(id);

		for (Post p1 : posts) {
			if (p1.getId().equals(postid)) {
				request.setAttribute("post", p1);
			} 
		}
		CookieHandler uCookie = new CookieHandler("user");
		if (uCookie.checkForCookie(request)) {
			
			AuthToken aToken = new AuthToken(uCookie.getCookie().getValue());
			if (aToken.parseToken()) {
				List<User> ulist = userDao.getUsers();
				for (User u: ulist) {
					if (u.getId().equals(aToken.getUid())) {				
						request.setAttribute("user", u.getUsername());
					}
				}
			}
		}
		
		request.setAttribute(FORUM, "");
		request.setAttribute(WRITE, "");
		request.setAttribute(READ, READ);					
		request.setAttribute(PART1, "");
		request.setAttribute(PART2, "");
		request.setAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper Forums -- Read a Post");			
		return PUBLIC_PAGE;
	}	

	@RequestMapping(value = "/public", method = RequestMethod.POST, params={"addComment"})
	public String addComment(@ModelAttribute("postComment") PostComment postComment,
		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		String message = "There is a problem with your request.  You may need to logout and login again to continue.";
		if (csrfTokenChecker.isValid(postComment.getCsrfToken(), request)) {
			postDao.addComment(postComment);			
			message = "Your comment has been added!";
		}
		request.setAttribute("message", message);
		Integer postid = new Integer(postComment.getPostid());
		List<Post> posts= postDao.getPostsWithComments();
			
		for (Post p1 : posts) {
			if (p1.getId().equals(postid)) {
				request.setAttribute("post", p1);
			} 
		}

		NotificationCookie nCookie = new NotificationCookie();
		Notifications n;
		if (!nCookie.checkForCookie(request)) {
			n = new Notifications();
		} else {
			n = new Notifications(nCookie.getNotificationsCookie().getValue());
		}
		n.setComment(true);
		nCookie.addCookie(response, n);
		
		request.setAttribute(FORUM, "");
		request.setAttribute(WRITE, "");
		request.setAttribute(READ, READ);					
		request.setAttribute(PART1, "");
		request.setAttribute(PART2, "");
		request.setAttribute(TITLE_ATTRIBUTE_NAME, "Habit Helper Forums -- Read a Post");			
		return PUBLIC_PAGE;
	}
	
}