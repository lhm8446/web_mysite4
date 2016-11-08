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
		return guestBookDao.getList();
	}
	
	public List<GuestBookVo> getList(int page){
		return guestBookDao.getList(page);
	}
	
	public void insert(GuestBookVo vo){
		Long no = guestBookDao.insert(vo);
		System.out.println(no);
	}
	
	public GuestBookVo insert2(GuestBookVo vo){
		Long no = guestBookDao.insert(vo);
		System.out.println(no);
		return null;
	}
	
	public void delete(GuestBookVo vo){
		guestBookDao.delete(vo);
	}
}
