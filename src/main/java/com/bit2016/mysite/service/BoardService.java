package com.bit2016.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2016.mysite.repository.BoardDao;
import com.bit2016.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	private static final int PAGE_SIZE = 5;
	private static final int LIST_SIZE = 5;
	
	
	@Autowired
	private BoardDao boardDao;
	
	public List<BoardVo> getList(int currentpage){
		List<BoardVo> list = boardDao.getList(currentpage, LIST_SIZE);
		return list;
	}
}
