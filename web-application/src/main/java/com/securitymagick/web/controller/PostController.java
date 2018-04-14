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

import com.securitymagick.domain.Post;
import com.securitymagick.domain.PostComment;
import com.securitymagick.domain.dao.PostDao;
import com.securitymagick.web.cookie.CookieHandler;

@Controller
public class PostController {
	@Autowired
	PostDao postDao;
	

	@RequestMapping(value = "/post", method = RequestMethod.GET)
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
			request.setAttribute("user", uCookie.getCookie().getValue());
		}
		
		return "redirect:/public?read=yes&id=" + postid.toString();

	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String addComment(@ModelAttribute("postComment") PostComment postComment,
		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		postDao.addComment(postComment);	
		Integer postid = new Integer(postComment.getPostid());
		String message = "Your comment has been added!";
		request.setAttribute("message", message);
		List<Post> posts= postDao.getPostsWithComments();
			
		for (Post p1 : posts) {
			if (p1.getId().equals(postid)) {
				request.setAttribute("post", p1);
			} 
		}
		
		return "redirect:/public?read=yes&id=" + postid.toString();
	}
}