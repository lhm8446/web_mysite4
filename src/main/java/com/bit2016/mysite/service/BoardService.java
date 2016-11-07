package com.bit2016.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2016.mysite.repository.BoardDao;
import com.bit2016.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	private static final int LIST_SIZE = 5; //리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; //페이지 리스트의 페이지 수
	
	
	@Autowired
	private BoardDao boardDao;
	
	public Map<String, Object> getList(int currentPage, String keyword){
		
		// 1. 페이징을 위한 기본 데이터 계산
		int totalPost = boardDao.getTotalCount(keyword); // 전체 게시물 카운트 
		int totalPage = (int)Math.ceil( (double)totalPost / LIST_SIZE ); //전체 게시물 갯수를 리스팅될 수로 나눠 전체 페이지 수 계산
		int totalBlock = (int)Math.ceil( (double)totalPage / PAGE_SIZE ); //페이지 리스트 블록 카운트 
		int currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE ); // 현재 블록 계산
		
		//2. 파라미터 page 값  검증
		if( currentPage < 1 ) {
			currentPage = 1;
			currentBlock = 1;
		} else if( currentPage > totalPage ) {
			currentPage = totalPage;
			currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
		}
		
		//3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1)*PAGE_SIZE + 1;
		int prevPage = ( currentBlock > 1 ) ? ( currentBlock - 1 ) * PAGE_SIZE : 0;
		int nextPage = ( currentBlock < totalBlock ) ? currentBlock * PAGE_SIZE + 1 : 0;
		int endPage = ( nextPage > 0 ) ? ( beginPage - 1 ) + LIST_SIZE : totalPage;
		
		
		//4. 리스트 가져오기
		List<BoardVo> list = boardDao.getList( keyword, currentPage, LIST_SIZE );

		
		//5. 리스트 정보를 맵에 저장
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put( "list", list );
				map.put( "totalPost", totalPost );
				map.put( "listSize", LIST_SIZE );
				map.put( "currentPage", currentPage );
				map.put( "beginPage", beginPage );
				map.put( "endPage", endPage );
				map.put( "prevPage", prevPage );
				map.put( "nextPage", nextPage );
				map.put( "keyword", keyword );
				
				return map;
	}
}
