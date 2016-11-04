package com.bit2016.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit2016.mysite.service.UserService;
import com.bit2016.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/joinform")
	public String joinForm(){
		return "user/joinform";
	}
	
	
	@RequestMapping("/loginform")
	public String loginForm(){
		return "user/loginform";
	}
	
	
	@RequestMapping("/join")
	public String join(@ModelAttribute UserVo vo){
		
		userService.join(vo);
		
		return "redirect:/user/joinsuccess";
	}
	
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess(){
		return "user/joinsuccess";
	}
	
	
	@RequestMapping("/login")
	public String login(@RequestParam (value="email", required=true, defaultValue="") String email,
			@RequestParam (value="password", required=true, defaultValue="") String password,
			HttpSession session){
		
		UserVo userVo = userService.login(email, password);
		
		//인증 실패
		if(userVo ==null){
			return "redirect:/user/loginform?result=fail";
		}
		
		//인증 처리
		session.setAttribute("authUser", userVo);
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
	
	
	@RequestMapping("/modifyform")
	public String modifyForm(HttpSession session,Model model){
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		//접근제한
		if(authUser == null){
			return "redirect:/user/loginform";
		}
		
		UserVo vo = userService.getUser(authUser.getNo());
		
		model.addAttribute("userVo", vo);
		
		return "user/modifyform";
	}
	
	
	@RequestMapping("/modify")
	public String modify(HttpSession session, @ModelAttribute UserVo vo){
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		//접근제한
		if(authUser == null){
			return "redirect:/user/loginform";
		}
		
		vo.setNo(authUser.getNo());   //안전하게 현재 로그인되있는 유저의 no 값을 가져옴
		userService.updateUser(vo);
		authUser.setName(vo.getName());
		
		return "redirect:/user/modifyform?update=success";
	}
}
