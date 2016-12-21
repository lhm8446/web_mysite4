package com.bit2016.mysite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bit2016.dto.JSONResult;
import com.bit2016.mysite.service.GalleryService;
import com.bit2016.mysite.vo.GalleryVo;
import com.bit2016.mysite.vo.UserVo;
import com.bit2016.security.Auth;
import com.bit2016.security.AuthUser;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	@ResponseBody
	@RequestMapping("/api")
	public JSONResult json(){
		List<GalleryVo> list = galleryService.getList();

		return JSONResult.success(list);
	}
	
	@RequestMapping("")
	public String index(Model model){
		
		List<GalleryVo> list = galleryService.getList();
		model.addAttribute("list", list);

		return "gallery/index";
	}
	
	@RequestMapping("/form")
	public String form(){
		return "gallery/form";
	}
	
	@Auth
	@RequestMapping("/upload")
	public String upload(@RequestParam(value="file1") MultipartFile file1,
						 @AuthUser UserVo authUser,Model model){
		
		Long no = authUser.getNo();
		
		String url1 = galleryService.restore(file1,no);
		
		model.addAttribute("url1", url1);
		
		return "redirect:/gallery";
	}
}
