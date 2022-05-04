package com.kh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// static : 객체생성할 필요없음, 싱글톤 패턴
public class JDBCTemplate {
	
	// 1. Connection 객체 생성 (DB 접속) 한 후 해당 Connection 객체를 반환하는 메소드
	public static Connection getConnection() {
		
		// 동적코딩방식으로 설정값 가져오기
		Properties prop = new Properties();  // Map 계열의 컬렉션 (key - value)
		
		// 읽어들이고자 하는 driver.properties 파일의 경로를 알아오기
		// 배포되는 시점에는 src 라는 폴더는 WebContent 폴더 내에 없기 때문에
		// src를 시작점으로 잡으면 오류 발생 => classes 폴더를 시작점으로 잡아줄 것
		String fileName = JDBCTemplate.class.getResource("/sql/driver/driver.properties").getPath();
		
		// fileName 변수에 담긴 값
		// C:\06_Web-workspace2\JSP_Project\WebContent\WEB-INF\classes\sql\driver\driver.properties
		
		try {
			//연결통로 ? 
			prop.load(new FileInputStream(fileName));
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Connection conn = null;
	
		try {
			// 1) JDBC Driver 등록
			Class.forName(prop.getProperty("driver"));
			
			// 2) DB 와 접속 정보를 갖고있는 Connection 객체 생성
			conn = DriverManager.getConnection(prop.getProperty("url"),
					                           prop.getProperty("username"),
					                           prop.getProperty("password"));
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return conn;  //conn 지역변수라서 전역변수로 바꿈
	}
	
	
	// 2. 전달받은 Connection 객체를 가지고 commit 해주는 메소드
	public static void commit(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// 3. 전달받은 Connection 객체를 가지고 rollback 해주는 메소드
	public static void rollback(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// 4. 전달받은 Connection 객체를 반납시켜주는 메소드
	public static void close(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 5. 전달받은 Statement 객체를 반납시켜주는 메소드
	//=> 다형성에 의해 자식클래스인 PreparedStatement 도 반납 가능
	public static void close(Statement stmt) {
		
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	// 6. 전달받은 ResultSet 객체를 반납시켜주는 메소드
	public static void close(ResultSet rset) {
		
		try {
			if(rset != null && ! rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}












