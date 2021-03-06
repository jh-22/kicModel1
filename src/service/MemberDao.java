package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Member;
import util.JdbcConection;

public class MemberDao {
	public int insertMember(HttpServletRequest request	) {
		
		//DB작업 해야되니까 Connection , PreparedStatement 
		
		
		Connection con = JdbcConection.getConection(); //JdbcConection 클래스에 만들어둔 getConection()메서드를 실행-> DB연결 생성
		PreparedStatement pstmt = null; //sql실행하기위해 PreparedStatement(사전에 컴파일된 SQL문 실행) 생성
		
		//sql만들기  : 7개 member 클래스 변수 갯수 만큼, sql나눠서 작성할때 띄어쓰기 주의
		String sql = "insert into member "+"values(?,?,?,?,?,?,?)";
		
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("id"));
			pstmt.setString(2, request.getParameter("pass"));
			pstmt.setString(3, request.getParameter("name"));
			pstmt.setInt(4, Integer.parseInt(request.getParameter("gender")));
			pstmt.setString(5, request.getParameter("tel"));
			pstmt.setString(6, request.getParameter("email"));
			pstmt.setString(7, request.getParameter("picture"));
			/* 순서는 sql에서 만든 테이블 컬럼명 순서 */
		
			
			return pstmt.executeUpdate(); //dml일때 executeUpdate();  -> sql : insert~~
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConection.close(con, pstmt, null);  //JdbcConection 클래스에 만들어둔 close()메서드 이용 
			// ResultSet 사용 안했으니까 rs 자리에 null 입력
		}
		
		
		return 0;
	}
	
	public Member selectOne(String id) {
		
		Connection con = JdbcConection.getConection();
		PreparedStatement pstmt = null;
		String sql = "select * from member where id = ?";
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery(); //select일때 executeQuery(); -> sql : select~
			
			//id ; primary -> while문 사용할 필요 없어, 여러개면 while문 써야해
			if(rs.next()) {
				Member m = new Member(
						rs.getString("id"),
						rs.getString("pass"),
						rs.getString("name"),
						rs.getString("tel"),
						rs.getString("email"),
						rs.getString("picture"),
						rs.getInt("gender")
						);
				return m;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConection.close(con, pstmt, rs);
		}
		
		
		return null;
	}
	
	public int memberUpdate(Member mem) {
		Connection con = JdbcConection.getConection();
		PreparedStatement pstmt = null;
		

		String sql = "update member set tel = ?, email = ?, picture =? where id = ?";
		
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem.getTel());
			pstmt.setString(2, mem.getEmail());
			pstmt.setString(3, mem.getPicture());
			pstmt.setString(4, mem.getId());
			
			return pstmt.executeUpdate(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConection.close(con, pstmt, null);
		}
		
		
		return 0;
	}
	
	public int deleteMember(String id) { 
		Connection con = JdbcConection.getConection();
		PreparedStatement pstmt = null;
		

		String sql = "delete from member where id = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			return pstmt.executeUpdate(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConection.close(con, pstmt, null);
		}
		
		
		
		return 0;
	}
	
	public int changePass(String id, String newpass) {
		Connection con = JdbcConection.getConection();
		PreparedStatement pstmt = null;
		

		String sql = "update member set pass = ? where id = ?";
		
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newpass);
			pstmt.setString(2, id);
	
			
			return pstmt.executeUpdate(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConection.close(con, pstmt, null);
		}
		
		
		return 0;
	}
	
	
	public List<Member> memberList() {
		
		Connection con = JdbcConection.getConection();
		PreparedStatement pstmt = null;
		String sql = "select * from member ";
		ResultSet rs = null;
		List<Member> li = new ArrayList<>();
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(); 
			
			while(rs.next()) {
				Member m = new Member(
						rs.getString("id"),
						rs.getString("pass"),
						rs.getString("name"),
						rs.getString("tel"),
						rs.getString("email"),
						rs.getString("picture"),
						rs.getInt("gender")
						);
				li.add(m);
			}
			return li;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConection.close(con, pstmt, rs);
		}
		
		
		return null;
	}
	
}
