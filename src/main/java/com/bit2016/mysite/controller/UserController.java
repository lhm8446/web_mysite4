package com.bit2016.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit2016.mysite.service.UserService;
import com.bit2016.mysite.vo.UserVo;
import com.bit2016.security.Auth;
import com.bit2016.security.AuthUser;

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
	
	
//	@RequestMapping("/login")
//	public String login(@RequestParam (value="email", required=true, defaultValue="") String email,
//			@RequestParam (value="password", required=true, defaultValue="") String password,
//			HttpSession session){
//		
//		UserVo userVo = userService.login(email, password);
//		
//		//인증 실패
//		if(userVo ==null){
//			return "redirect:/user/loginform?result=fail";
//		}
//		
//		//인증 처리
//		session.setAttribute("authUser", userVo);
//		return "redirect:/";
//	}
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session){
//		session.removeAttribute("authUser");
//		session.invalidate();
//		
//		return "redirect:/";
//	}
	
	
	@Auth
	@RequestMapping("/modifyform")
	public String modifyForm(@AuthUser UserVo authUser, Model model){
		
		UserVo vo = userService.getUser(authUser.getNo());
		
		model.addAttribute("userVo", vo);
		
		return "user/modifyform";
	}
	
	@Auth
	@RequestMapping("/modify")
	public String modify(@AuthUser UserVo authUser, @ModelAttribute UserVo vo){

		vo.setNo(authUser.getNo());   //안전하게 현재 로그인되있는 유저의 no 값을 가져옴
		userService.updateUser(vo);
		authUser.setName(vo.getName());
		
		return "redirect:/user/modifyform?update=success";
	}
	
//	@ExceptionHandler(UserDaoException.class)
//	public String handlerUserDaoException(){
//		// 1. logging (파일에 내용 저장)
//		// 2. 사용자에게 안내(error) 페이지
//		return "error/500";
//	}
}
