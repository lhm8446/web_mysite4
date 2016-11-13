package com.bit2016.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit2016.mysite.service.BoardService;
import com.bit2016.mysite.vo.BoardVo;
import com.bit2016.mysite.vo.UserVo;
import com.bit2016.security.Auth;
import com.bit2016.security.AuthUser;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String getList(@RequestParam(value="p", required=true, defaultValue="1")Integer page,
						  Model model) {
		Map<String, Object> map = boardService.getList(page);
		model.addAttribute("map",map);
		return "/board/list";
	}
	
	@RequestMapping("/view")
	public String viewPost(@RequestParam(value="no", required=true, defaultValue="")Long boardNo,
			Model model){
		BoardVo vo = boardService.getPost(boardNo);
		model.addAttribute("vo", vo);
		
		return "/board/view";
	}
	
	@Auth
	@RequestMapping(value="/writeform", method=RequestMethod.GET)
	public String writeForm(){
		return "/board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo, @AuthUser UserVo authUser){
		vo.setUserNo(authUser.getNo());
		boardService.insert(vo);
		return "redirect:/board";
	}
	
	@RequestMapping("/reply")
	public String reply(){
		return "/board/reply";
	}
	
	
	
	
	
	
}
