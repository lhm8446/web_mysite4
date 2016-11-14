package com.bit2016.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.bit2016.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestBookVo> getList(){
		StopWatch stopWatch = new  StopWatch();
		stopWatch.start();
		List<GuestBookVo> list=  sqlSession.selectList("guestbook.getList");
		stopWatch.stop();
		
		System.out.println("exectuime guestBookDao.getlIst  : " + stopWatch.getTotalTimeMillis());
		return list;
	}
	
	public List<GuestBookVo> getList(int page){
		return sqlSession.selectList("guestbook.getListByPage", page);
	}
	
	public Long insert(GuestBookVo vo){
		sqlSession.insert("guestbook.insert", vo);
		return vo.getNo();
	}
	
	public void delete(GuestBookVo vo){
		sqlSession.delete("guestbook.delete", vo);
	}
}
