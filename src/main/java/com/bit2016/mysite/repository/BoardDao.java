package com.bit2016.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bit2016.mysite.vo.BoardVo;
import com.bit2016.mysite.vo.UserVo;

@Repository
public class BoardDao {
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection( url, "webdb", "webdb" );
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩실패 : " + e);
		}
		
		return conn;
	}
	
	public void update(BoardVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update board set order_no = order_no +1 where group_no = ? and order_no > ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, vo.getGroupNo());
			pstmt.setInt(2, vo.getOrderNo());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally{
			try{
				if(pstmt !=null){
					pstmt.close();
				}
				if(conn !=null){
					conn.close();
				}
			}catch(SQLException e) {
				System.out.println("error : " + e);
			} 
		}
	}

	public void newWrite(BoardVo vo,UserVo authUser){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into board values(board_seq.nextval, ?, ?, sysdate, 0, nvl((select max(group_no) from board),0)+1 , 1, 0 ,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, authUser.getNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		}finally{
			try {
				if(pstmt != null){
					pstmt.close();
				}
				if(conn !=null){
				conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
	}
	public void reWrite(BoardVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into board values(board_seq.nextval,'?, ?, sysdate, ?, 2, -- 부모글의 그룹--  2, -- 부모글 순서 +1--  1, -- 부모글 깊이 +1-	   2)";

			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getHit());
			pstmt.setInt(4, vo.getGroupNo());
			
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		}finally{
			try {
				if(pstmt != null){
					pstmt.close();
				}
				if(conn !=null){
				conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
		
	}
	
	public int getTotalCount( String keyword ) {
		int totalCount = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			if( "".equals( keyword ) ) {
				String sql = "select count(*) from board";
				pstmt = conn.prepareStatement(sql);
			} else { 
				String sql =
					"select count(*)" +
					"  from board" +
					" where title like ? or content like ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
			}
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				totalCount = rs.getInt( 1 );
			}
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
		
		return totalCount;
	}
	
	public BoardVo viewPost(Long uno){
		BoardVo vo = null;
		
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = getConnection();
		
			String sql = "select no, title, content, hit, group_no, order_no,depth from board where no = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, uno);
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				long no = rs.getLong( 1 );
				String title = rs.getString( 2 );
				String content = rs.getString(3);
				int hit = rs.getInt( 4 );
				int gno = rs.getInt( 5 );
				int ono = rs.getInt( 6 );
				int depth = rs.getInt( 5 );
				
				vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setHit(hit);
				vo.setGroupNo(gno);
				vo.setOrderNo(ono);
				vo.setDepth(depth);
			}
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
				if(conn != null){
				conn.close();
			}
			}catch(SQLException e){
				System.out.println("error : " + e);
			}
		}
		return vo;
	}
	public List<BoardVo> getList(String keyword, Integer page, Integer size){
		
		List<BoardVo> list = new ArrayList<BoardVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			if( "".equals( keyword ) ) {
				String sql = 
					" select * " +
					"   from ( select no, title, hit, reg_date, depth, name, users_no, rownum as rn" +
					"            from(  select a.no, a.title, a.hit, to_char(a.reg_date, 'yyyy-mm-dd hh24:mi:ss') as reg_date, a.depth, b.name, a.users_no" +
					"                     from board a, users b" +
					"                    where a.users_no = b.no" +
	                "                 order by group_no desc, order_no asc ))" +
	                "  where (?-1)*?+1 <= rn and rn <= ?*?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt( 1, page );
				pstmt.setInt( 2, size );
				pstmt.setInt( 3, page );
				pstmt.setInt( 4, size );
			} else {
				String sql = 
					" select * " +
					"   from ( select no, title, hit, reg_date, depth, name, users_no, rownum as rn" +
					"            from(  select a.no, a.title, a.hit, to_char(a.reg_date, 'yyyy-mm-dd hh24:mi:ss') as reg_date, a.depth, b.name, a.users_no" +
					"                     from board a, users b" +
					"                    where a.users_no = b.no" +
					"                      and (title like ? or content like ?)" + 
					"                 order by group_no desc, order_no asc ))" +
					"  where (?-1)*?+1 <= rn and rn <= ?*?";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString( 1, "%" + keyword + "%" );
				pstmt.setString( 2, "%" + keyword + "%" );
				pstmt.setInt( 3, page );
				pstmt.setInt( 4, size );
				pstmt.setInt( 5, page );
				pstmt.setInt( 6, size );
			}
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				long no = rs.getLong( 1 );
				String title = rs.getString( 2 );
				int hit = rs.getInt( 3 );
				String regDate = rs.getString( 4 );
				int depth = rs.getInt( 5 );
				String userName = rs.getString( 6 );
				long userNo = rs.getLong( 7 );
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setDepth(depth);
				vo.setUserName(userName);
				vo.setUserNo(userNo);
				
				list.add( vo );
			}
			
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
		
		return list;
	}
	
}
