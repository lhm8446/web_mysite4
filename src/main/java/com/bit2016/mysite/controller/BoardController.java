package com.bit2016.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit2016.mysite.service.BoardService;
import com.bit2016.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String getList(Model model) {
		List<BoardVo> list = boardService.getList();
		model.addAttribute("list",list);
		return "/board/list";
	}
}
