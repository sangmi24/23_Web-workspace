package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) 인코딩 셋팅 (Post 방식인 경우에만 해당)
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달값을 뽑아서 변수에 기록하고 VO 객체로 가공하기
		String userId = request.getParameter("userId"); //필수입력
		String userPwd = request.getParameter("userPwd"); // 필수입력
	    String userName = request.getParameter("userName"); //필수입력
	    String phone = request.getParameter("phone"); // 빈문자열 ""이 올수도 있다. 
	    String email = request.getParameter("email"); // 빈문자열 ""이 올수도 있다. 
	    String address = request.getParameter("address"); //빈문자열 ""이 올수도 있다. 
	    String[] interestArr = request.getParameterValues("interest"); // ["운동","등산",..] / null
	    
	    // 3) String[] --> String 
	    // ["운동","등산"] --> "운동,등산"
	    String interest = "";  //null이라면 ""로 나옴 (else가 필요없음)
	    
	    if(interestArr != null) {
	    	interest = String.join(", ", interestArr);
	    }
	    
	    // 매개변수 생성자를 이용해서 Member 객체에 담기
	    Member m = new Member(userId,userPwd,userName,phone,email,address,interest);
	    
	    // 3) 요청 처리 (MemberService 클래스의 어떤 메소드를 호출할때 전달값을 같이 넘겨서 결과 받기 
		int result = new MemberService().insertMember(m);
		
		// 4) 처리 결과를 가지고 사용자가 보게 될 응답 뷰 지정
		if(result >0 ) { //성공 => 메인페이지 (url 재요청 방식)
			
			// 우선적으로 session에 현재 회원가입에 성공한 회원의 정보를 담아둘것
			// => 메인페이지로 이동 시 자동으로 로그인한 효과를 주겠다.
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", m );
			
			// => 성공 알람메세지도 같이 담기
			session.setAttribute("alertMsg", "회원가입에 성공했습니다.");
			
		   // response.sendRedirect("url 요청값");
			response.sendRedirect(request.getContextPath()); //메인페이지로 
			
			
		}
		else { //실패 => 에러페이지 포워딩
			
			//4_1) 응답페이지에서 필요로 하는 데이터를 담기(request 의  attribute에 담기)
			request.setAttribute("errorMsg", "회원가입에 실패했습니다.");
			
			//4_2) 응답하고자 하는 jsp 파일 경로를 제시하면서 RequestDispatcher 객체 생성
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
          
			//4_3) 포워딩
			view.forward(request, response);
			
		}
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
