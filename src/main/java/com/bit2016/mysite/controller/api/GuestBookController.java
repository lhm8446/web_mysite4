package com.bit2016.mysite.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit2016.mysite.service.GuestBookService;
import com.bit2016.mysite.vo.GuestBookVo;

@Controller("guestbookAPIController")
@RequestMapping("/guestbook/api")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestBookService;
	
	@ResponseBody
	@RequestMapping("/list")
	public Object list(@RequestParam(value="p", required=true, defaultValue="1")Integer page){
		
		List<GuestBookVo> list = guestBookService.getList(page);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("result", "success");
		map.put("data", list);
		
		return map;
		
	}	
}
