package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {

	// 로그인 요청 서비스
	public Member loginMember(String userId, String userPwd) {
		
		//1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		//2) 만들어진 Connection 객체와 전달받았던 값들을 DAO한테 넘기기
		Member m = new MemberDao().loginMember(conn,userId, userPwd);
		
		//3) INSERT, UPDATE, DELETE 구문의 경우 commit 또는 rollback
		//=> SELECT 문일 경우는 생략
		
		// 4) Connection 객체를 반납
		JDBCTemplate.close(conn);
		
		//5) Controller 한테 결과 반환
		return m;		
	 }
	
	 // 회원 가입용 서비스
	public int insertMember(Member m) {
		
	 //1) Connection 객체 생성	
		Connection conn = JDBCTemplate.getConnection();
		
	 //2) MemberDao 의 어떤 메소드를 호출(Connection 객체와 전달값을 넘기면서)
		int result = new MemberDao().insertMember(conn, m);
		
	 //3) 결과값에 따라 commit 또는 rollback 처리 (select문일 경우는 생략)
	 if(result >0 ) { // 성공
		 JDBCTemplate.commit(conn);		 
	 }
	 else { //실패
		 JDBCTemplate.rollback(conn);
	 }
	
	 //4) Connection 객체 반납
	 JDBCTemplate.close(conn);
		
	 //5) 결과값을 Controller 로 반환
	 return result;
	 
	}
	// 회원 정보 수정용 서비스
	public Member updateMember(Member m) {
		
		//1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		//2) 만들어진 Connection 객체와 전달값을 DAO 로 넘겨서 결과 받기
		int result = new MemberDao().updateMember(conn,m);
		
		//3) 결과에 따른 commit,rollback 처리
		Member updateMem = null;
		
		if(result>0) { //성공
			
			JDBCTemplate.commit(conn);
			// 갱신된 회원의 정보를 다시 조회해오기 (리턴용)
			updateMem = new MemberDao().selectMember(conn,m.getUserId());
			
		}
		else { //실패
			JDBCTemplate.rollback(conn);
			
		}
		//4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		//5) Controller 로 결과 반환
		return updateMem;
		
		//조회되는 것이 없으면 null이 담김 		
	}
	
	// 회원 비밀번호 변경용 서비스
	public Member updatePwdMember(String userId, String userPwd, String updatePwd) {
		
		//1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		//2) 만들어진 Connection 객체와 전달값을 DAO 로 넘겨서 결과 받기
		int result = new MemberDao().updatePwdMember(conn,userId,userPwd,updatePwd);
		
		//3) 결과에 따른 commit,rollback 처리
		Member updateMem = null; //실패할때는 null값이 리턴됨
		
		if(result>0) { //성공
			
			JDBCTemplate.commit(conn);
			
			updateMem = new MemberDao().selectMember(conn, userId);
			// 회원 한명갱신된 정보를 가져옴(재활용함)service단에서 
			
		}
		else { //실패
			JDBCTemplate.rollback(conn);
		}
		
		//4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		//5) 결과 반환
		return updateMem;		
	}
	
	// 회원 탈퇴용 서비스
	public int deleteMember(String userId, String userPwd) {
         
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) 만들어진 Connection 객체와 전달값을 DAO 한테 보내주기
		int result = new MemberDao().deleteMember(conn,userId,userPwd);
		
		// 3) 성공 실패 여부에 따라 commit, rollback 처리
		if(result>0) {
			
			JDBCTemplate.commit(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		//4) Connection 자원 반납
		JDBCTemplate.close(conn);
		
		//5) 결과 반환
		return result;
		
	}
	
	
	
	
	
}



