package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Board;
import util.JdbcConection;

public class BoardDao {
//Data Access Object : Dao
	
	public int nextNum() {
		Connection con = JdbcConection.getConection();
		PreparedStatement pstmt = null;
		
		String sql = "select boardseq.nextval from dual";
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			rs.next();
			
			return rs.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcConection.close(con, pstmt, rs);
		}
		return 0;
		
	}
	
	public void refStepAdd(int ref, int refstep) {
		
		Connection con = JdbcConection.getConection();
		PreparedStatement pstmt = null;		
		String sql = "update board set refstep = refstep + 1 "
				+" where ref = ? and refstep > ? ";
			//원글이 같고 기존refstep 보다 클경우 업데이트
		
		
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, refstep);
			
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConection.close(con, pstmt, null);
		}
	}
	
	
	 public int insertBoard(Board board) {
		 
		 	Connection con = JdbcConection.getConection();
			PreparedStatement pstmt = null;
			
			String sql = "insert into board "+
					"values(?,?,?,?,?,?,?,sysdate,?,0,?,?,?)";
			//board  table(sql) 순서 확인하기 --> class생성시 순서 똑같이 만들면 덜 헷갈려
			//아니면 컬럼명 쓰기(컬럼명 생략시 순서지켜야함)
			//num : sequence nextval 사용할거니까 ? 대신 nextval입력
			//sysdate : 오라클에서 현재 날짜
			//readcnt : 0부터 시작이니까 0으로 잡기
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, board.getNum());
				pstmt.setString(2, board.getWriter());
				pstmt.setString(3, board.getPass());
				pstmt.setString(4, board.getSubject());
				pstmt.setString(5, board.getContent());
				pstmt.setString(6, board.getFile1());
				pstmt.setString(7, board.getBoardid());
				pstmt.setString(8, board.getIp());
				pstmt.setInt(9, board.getRef());
				pstmt.setInt(10, board.getReflevel());
				pstmt.setInt(11, board.getRefstep());
				
				return pstmt.executeUpdate(); 
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcConection.close(con, pstmt, null);
			}
		 
		 
		 return 0;
	 }
	 
	
	 
	 public int boardCount(String boardid) {
		 
		 Connection con = JdbcConection.getConection();
			PreparedStatement pstmt = null;
			
			String sql = "select count(*) from board where boardid =?";
			//boardid에 따른 row 값 count
			
			ResultSet rs = null;
			
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, boardid);
				
				rs = pstmt.executeQuery();
				
				rs.next(); //boolean, ????? select 결과의 존재 여부를 확인
				return rs.getInt(1); //?????  지정한 컬럼이 값을 int로 불러옴
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcConection.close(con, pstmt, rs);
			}
		 
		 
		 return 0;
	 }
	 
	 
public List<Board> boardList(int pageInt, int limit, int boardcount, String boardid) {
		 
		 Connection con = JdbcConection.getConection();
			PreparedStatement pstmt = null;
			
			String sql = "select * from ("+
						"select rownum rnum, a.* from( "+
						"select * from board where boardid = ? "+
						" order by ref desc, refstep asc) a) "+ //최근글 순서로 정렬
						"where rnum BETWEEN ? and ? ";  
						//BETWEEN start and end 
						/* 
						-- 1, 4, 7, 10
						-- start : (pageInt-1)*limit + 1;
						-- end : start + limit - 1;
						-- 1 p --> 1 ~ 3
						-- 2 p --> 4
						-- 3 p --> 7 
						*/
						
			ResultSet rs = null;
			
			List<Board> list = new ArrayList<>();
			
			
			
			try {
				
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, boardid);
				pstmt.setInt(2, (pageInt-1)*limit+1);
				pstmt.setInt(3, pageInt*limit);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Board b = new Board();
					b.setNum(rs.getInt("num"));
					b.setWriter(rs.getString("writer"));
					b.setPass(rs.getString("pass"));
					b.setSubject(rs.getString("subject"));
					b.setContent(rs.getString("content"));
					b.setFile1(rs.getString("file1"));
					b.setRef(rs.getInt("ref"));
					b.setRefstep(rs.getInt("refstep"));
					b.setReflevel(rs.getInt("reflevel"));
					b.setReadcnt(rs.getInt("readcnt"));
					b.setRegdate(rs.getDate("regdate"));
					list.add(b);
				}
				return list;
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcConection.close(con, pstmt, rs);
			}
		 
		 
		 return list;
	 }
	 
public Board boardOne(int num) {
	 
	 Connection con = JdbcConection.getConection();
		PreparedStatement pstmt = null;		
		String sql = "select * from board where num =?";
		ResultSet rs = null;
		
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board b = new Board();
				b.setNum(rs.getInt("num"));
				b.setWriter(rs.getString("writer"));
				b.setPass(rs.getString("pass"));
				b.setSubject(rs.getString("subject"));
				b.setContent(rs.getString("content"));
				b.setFile1(rs.getString("file1"));
				b.setRef(rs.getInt("ref"));
				b.setRefstep(rs.getInt("refstep"));
				b.setReflevel(rs.getInt("reflevel"));
				b.setReadcnt(rs.getInt("readcnt"));
				b.setRegdate(rs.getDate("regdate"));
				return b;
			}
			
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConection.close(con, pstmt, rs);
		}
	 
	 
	 return null;
}

	public int boardUpdate(Board board) {
		 Connection con = JdbcConection.getConection();
			PreparedStatement pstmt = null;		
			String sql = "update board set subject = ?, content = ? , file1 = ? "
					+" where num = ?";
			
			
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, board.getSubject());
				pstmt.setString(2, board.getContent());
				pstmt.setString(3, board.getFile1());
				pstmt.setInt(4, board.getNum());
				return pstmt.executeUpdate();
				
				
			
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcConection.close(con, pstmt, null);
			}
		
		
		return 0;
	}
	
	public void readCountUp(int num) {
		 Connection con = JdbcConection.getConection();
			PreparedStatement pstmt = null;		
			String sql = "update board set readcnt = readcnt + 1 "
					+" where num = ?";
			
			
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				
				pstmt.executeUpdate();
				
				
			
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcConection.close(con, pstmt, null);
			}
		
		
		
		
	}
	public int boardDelete(int num) {
		
		Connection con = JdbcConection.getConection();
			PreparedStatement pstmt = null;		
			String sql = "delete from board "+" where num = ?";
			
			
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				
				return pstmt.executeUpdate();
				
				
			
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcConection.close(con, pstmt, null);
			}
		
		
		return 0;
		
	}
}
