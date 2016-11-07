package com.bit2016.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2016.mysite.repository.GuestBookDao;
import com.bit2016.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	
	@Autowired
	private GuestBookDao guestBookDao;
	
	public List<GuestBookVo> getList(){
		List<GuestBookVo> list = guestBookDao.getList();
		return list;
	}
	
	public void insert(GuestBookVo vo){
		guestBookDao.insert(vo);
	}
	
	public void delete(GuestBookVo vo){
		guestBookDao.delete(vo);
	}
}
