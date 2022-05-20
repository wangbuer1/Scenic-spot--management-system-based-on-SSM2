package com.java.ssm.ticket.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.ssm.ticket.service.ScenicService;


/**
 * 用户试图处理器
 * @author hgy
 */
@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	ScenicService ss;
	
	
	//首页处理器
	@GetMapping(value = {"/index.html","/"})
	public String index(Model ui) {
		//保持数据给前台渲染
		ui.addAttribute("ss", ss.getAll());
		return "index";
	}
	
	//登录处理器
	@RequestMapping("/login")
	public String login(Model ui){
		ui.addAttribute("ss", ss.getAll());
		return "login";
	}
	
	//注册处理器
	@RequestMapping("/register")
	public String register(Model ui){
		ui.addAttribute("ss", ss.getAll());
		return "register";
	}
}
