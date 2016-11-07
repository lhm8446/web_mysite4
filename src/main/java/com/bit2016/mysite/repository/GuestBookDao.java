package com.bit2016.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2016.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public List<GuestBookVo> getList(){
		return sqlSession.selectList("guestbook.getList");
	}
	
	public void insert(GuestBookVo vo){
		sqlSession.insert("guestbook.insert", vo);
	}
	
	
	public void delete(GuestBookVo vo){
		sqlSession.delete("guestbook.delete", vo);
	}
}
