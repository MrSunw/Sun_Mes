package com.sun.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.model.SysUser;
import com.sun.service.UserService;

@Controller
@RequestMapping("/test")
public class UserController {

	@Resource
	private UserService userService;
	
	@RequestMapping("/insert")
	@ResponseBody
	public ModelAndView testInsert(SysUser user) {
		userService.insert(user);
		ModelAndView model=new ModelAndView();
		SysUser newUser=userService.select(10);
		model.addObject("user", newUser);
		System.out.println(user);
		model.setViewName("testuser");
		return model;
		
		
	}
	@RequestMapping("/inserts")
	public ModelAndView testInserts() {
		ModelAndView model=new ModelAndView();
		model.setViewName("testuser");
		return model;
		
		
	}
}
