package com.securitymagick.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.securitymagick.domain.Post;
import com.securitymagick.domain.dao.PostDao;

@Controller
public class PostsController {
	@Autowired
	PostDao postDao;

	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String showPost(HttpServletRequest request) {
		List<Post> posts= postDao.getPosts();
		request.setAttribute("posts", posts);
		return "redirect:/public?read";

	}

}