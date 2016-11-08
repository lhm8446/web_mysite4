package com.bit2016.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit2016.mysite.service.UserService;

@Controller("userAPIController")
@RequestMapping("/user/api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public Map<String,Object> checkEmail(@RequestParam(value="email", required=true, defaultValue="")String email){
		
		boolean result = userService.emailExists(email);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("result", "success");
		
		if(result){
			map.put("data", "exist");
		}else{
			map.put("data", "not exist");
		}
		
		return map;
	}
}
