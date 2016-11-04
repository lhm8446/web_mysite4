package com.bit2016.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("")
	public String index(){
		return "main/index";
	}
	
	@RequestMapping("/main")
	public String main(){
		return "main/index";
	}
	
	@RequestMapping("/guestbook/ajax")
	public String guestbookAjax(){
		return "guestbook/list-ajax";
	}
	
}
