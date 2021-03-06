<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="com.kh.member.model.vo.Member" %>    
<%
   // 로그인한 사용자의 정보 뽑기 (session으로부터)
   Member loginUser = (Member)session.getAttribute("loginUser");

   // System.out.println(loginUser);
   // 로그인 전 menubar.jsp를 로딩시 loginUser에  null이 담김
   // 로그인 후 menubar.jsp를 로딩시 loginUser에 해당 회원의 정보가 담긴 Member객체가 담김
   
   //request.getContextPath()메소드를 이용해서 context root값을 알아오기
   String contextPath = request.getContextPath();
   
   // System.out.println(contextPath);
   
   // 세션으로부터 알람메세지 뽑아내기
   String alertMsg = (String)session.getAttribute("alertMsg");
   
   //System.out.println(alertMsg);
   //로그인 서비스 요청 전 menubar.jsp 로딩시 null
   //로그인 서비스 요청 후 성공시 menubar.jsp 로딩 시 alert로 띄워줄 메세지 문구

   // 쿠키 불러오기 : request.getCookies() 메소드 => Cookie 배열로 리턴
   Cookie[] cookies = request.getCookies();
   
   //내가 원하는 쿠키를 골라내는 작업
   String saveId = "";
   if(cookies != null){
	   
	   for(int i=0; i<cookies.length; i++){
		  // System.out.println(cookies[i]+" / "+cookies[i].getName()+ " / "+ cookies[i].getValue());
	      // getName() : 쿠키의 키값
	      // getValue() : 쿠키의 밸류값
		  
		  if(cookies[i].getName().equals("saveId")){
			  
			  saveId = cookies[i].getValue();
			  break;
		  }
	   
	   }
	   // 이 시점에서 "saveId" 라는 키값을 가진 쿠키가 있다면 =>saveId 변수에 아이디가 담겨있을겻임
			   
			   
			   
   }
   
   
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome D Class</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<style>
    #login-form, #user-info { float: right;}
    #user-info a {
        text-decoration: none;
        color: black;
        font-size: 12px;
    }
    .nav-area { background-color: black;}
    .menu {
        display : table-cell;  /*인라인요소처럼 배치 가능*/
        height: 50px;
        width: 150px;  
    }  
    .menu a {
        text-decoration: none;
        color: white;
        font-size: 20px;
        font-weight: bold;
        display: block; 
        width: 100%;
        height: 100%;
        line-height: 50px; /*장평조절 : 위~아래에서 가운데 조정*/
    }
    .menu a:hover{
        background-color: darkgray;
    }

</style>

</head>
<body>

    <script>
     
     // script 태그 내에서 스크립틀릿과 같은 jsp 요소들을 사용할 수 있다. 
    
      var msg = "<%= alertMsg %>";  //"성공적으로 로그인이 되었습니다." /"null"
      
      if(msg != "null"){
    	  alert(msg);
    	  
    	  // 알림창을 띄워 준 후 session에 담긴 해당 메세지를
    	  // session.removeAttribute("키값") 메소드로 지워줘야 함
    	  // 안그러면 menubar.jsp가 로딩될때마다 매번 alert가 계속 뜸
    	 <% session.removeAttribute("alertMsg"); %>
    	  
      }
    
    </script>
 
    <h1 align="center">Welcome D Class</h1>

    <div class="login-area">

        <!--두가지 화면이 선택적으로 보여지게끔 -->
        
        <%if(loginUser == null) { %>
        <!--로그인 전에 보여지는 로그인 form -->
     
        <form id="login-form" action="<%= contextPath %>/login.me" method="post">
          <table > 
              <tr>
                  <th>아이디: </th>
                  <td><input type="text" name="userId" required></td>
              </tr>
              <tr>
                 <th>비밀번호: </th>
                 <td><input type="password" name="userPwd" required ></td> 
              </tr>
              <tr align="right">
               <th colspan="2">
                 <input type="checkbox" id="saveId" name="saveId" value="y">
                 <label for="saveId">아이디저장</label>
               </th>
               </tr>
              <tr>
                  <th colspan="2">
                      <button type="submit">로그인</button>
                      <button type="button" onclick="enrollPage();">회원가입</button>
                  </th>
              </tr>
             
          </table>
        </form>
        
        <script>
            function enrollPage() {
            	
            	// 회원가입 페이지로 이동시키는 역할  
            	//location.href = "<%= contextPath %>/views/member/memberEnrollForm.jsp";
            	// 웹 애플리케이션의 디렉토리 구조가 url에 노출되면 보안에 취약
            	
            	//단순한 정적인 페이지 요청이라고 해도 반드시 servlet을 거쳐갈것!
            	//=> url에 서블릿 맵핑값만 보이게끔 해줄것임
            	location.href="<%= contextPath %>/enrollForm.me";          	
            }
        </script>
        
        
        
        <% } else { %>
        
        <!--로그인 성공 후 xxx님 환영합니다 화면 -->
       
        <div id="user-info">
            <b><%= loginUser.getUserName() %>님</b> 환영합니다. <br><br>
            <div align="center">
                <a href="<%= contextPath %>/myPage.me">마이페이지</a>
                <a href="<%= contextPath %>/logout.me">로그아웃</a>
            </div>      
        </div>
           <% } %> 
                  
    </div>
    
    <script>
      //saveId 라는 변수에 저장된 값을 불러와서 아이디 입력창에 설정
      //아이디 저장하기 체크박스에 체크 수행
      // 자바스크립트 문자열은 " " / ''로 엮어야 한다. 
       $(function(){
    	  
    	   var saveId = "<%= saveId %>"; //"admin" / ""
    	   
    	   if(saveId != ""){ //빈 문자열 
    		   
    		   //쿠키가 있는 경우
    		   $("#login-form input[name=userId]").val(saveId);
    		   $("#saveId").attr("checked",true);
    		   
    	   }
    	   
    	   
    	   
       });
    
    </script>
    
    
    
    
      <br clear="both">
       <br>
    <!--메뉴바 영역-->
    <div class="nav-area" align="center">
        <!--(div.menu>a)*4 + 엔터 -->
        <div class="menu"><a href="<%= contextPath %>">HOME</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.no">공지사항</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.bo?currentPage=1">일반게시판</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.th">사진게시판</a></div>
    </div>



</body>
</html>