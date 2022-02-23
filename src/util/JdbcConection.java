package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JdbcConection {
	public static Connection getConection() {  //DB 연결
		//connection : oracle server 연결  sql 전달 객체
		Connection con = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		 //jdbc:oracle:thin:@localhost:1521:xe 오라클 연결 url 정해진것
		String id = "kic";
		String pw = "1111";
		try {
			Class.forName("oracle.jdbc.OracleDriver");  //인스턴스생성
			 con = DriverManager.getConnection(url,id,pw); //인스턴스를 통해 DBMS에 대한 연결 생성
			
			 System.out.println("ok db");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error db");
			e.printStackTrace();
		} 
		return con;
	}
	
	public static void close(Connection con, PreparedStatement pstmt, ResultSet rs) { // DB 연결 해지
		
		
				try {
					
					if(con !=null) {
						con.commit();
						con.close();
					}
					if(pstmt != null) pstmt.close();
					if(rs != null) rs.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
	}
}
