package com.bit2016.mysite.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello(){
		return "가나나나나ㅏ난나나나나나";
	}
	
}
