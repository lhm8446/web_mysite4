package com.bit2016.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit2016.mysite.service.BoardService;
import com.bit2016.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String getList(
			@RequestParam(value ="p", required = true, defaultValue ="1") int currentpage, 
			@RequestParam(value = "kwd", required= true, defaultValue = "")String keyword, Model model){
		System.out.println("들어왔엉");
		List<BoardVo> list = boardService.getList(currentpage);
		model.addAttribute("list",list);
		
		return "/board/list";
	}
	
	
	

}
