package com.bit2016.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bit2016.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
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
	public List<GuestBookVo> getList(){
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			conn = getConnection();
			
			stmt = conn.createStatement();
			
			String sql = "select rank() over(order by reg_date)rank, no, name, content, password, to_char(reg_date, 'yyyy-mm-dd hh:mi:ss')"
					+ "from guestbook order by reg_date desc";

			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				int rank = rs.getInt(1);
				Long no = rs.getLong(2);
				String name = rs.getString(3);
				String content = rs.getString(4);
				String password = rs.getString(5);
				String date = rs.getString(6);
				
				GuestBookVo vo = new GuestBookVo();
				
				vo.setRank(rank);
				vo.setNo(no);
				vo.setName(name);
				vo.setContent(content);
				vo.setPassword(password);
				vo.setReg_date(date);
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("erro : " + e);
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
				conn.close();
			}
			}catch(SQLException e){
				System.out.println("erro : " + e);
			}
		}
		
		return list;
	}
	
	public void insert(GuestBookVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into guestbook values (guestbook_seq.nextval, ? ,?,?,sysdate)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getPassword());
			
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
	
	
	public void delete(Long no, String password){
		Connection conn = null;
		PreparedStatement pstmt = null;

		try{
			conn = getConnection();
			
			String sql = "delete from guestbook where no = ? and password = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			pstmt.setString(2, password);
			
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			System.out.println("Error : " + e);
		}finally{
			try{
				if(pstmt != null){
					pstmt.close();
				}
				if(conn != null){
					conn.close();
				}
			}catch(SQLException e){
				System.out.println("Error : " + e);
			}
		}
	}
}
