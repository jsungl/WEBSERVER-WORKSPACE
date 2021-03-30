package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * web.xml에 servlet등록을 자동을 처리함. 
 * 
 * 다음과 같은 속성들을 가진다
 * - urlPatterns : String[] 한 servlet이 여러 url을 처리할수있도록
 * - name : String
 */
//@WebServlet(urlPatterns = {"/member/login"})
@WebServlet("/member/login")
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	
	MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. encoding처리
		//request.setCharacterEncoding("utf-8");
		
		
		//2. 사용자입력값처리
		String memberId = request.getParameter("memberId");
		String password = MvcUtils.getSha512(request.getParameter("password")); //단방향암호화라 로그인시 입력한 패스워드를 인코딩처리해서 db에 저장된 값과 비교
		String saveId = request.getParameter("saveId");
		//System.out.println("memberId@servlet = " + memberId);
		//System.out.println("password@servlet = " + password);
		System.out.println("saveId@servlet = " + saveId); //on
		
		
		//3. 업무로직 : memberId로 회원객체를 조회 
		//servlet이 처리해야할 업무로직 ->service에 맡긴다
		Member member = memberService.selectOne(memberId);
		//System.out.println("member@servlet = " + member);
		
		
		
		//로그인 성공/실패 여부 판단
		//1. 로그인성공 : member != null & password.equals(member.getPassword())
		//2. 로그인 실패
		// 아이디가 존재하지않을때 member == null
		// 비번이 틀릴때  member != null && ! password.equals(member.getPassword())
		
		// request.getSession(create) : 새로생성여부(기본값 true). 없다면 만들어서라도 와라
		HttpSession session = request.getSession(); //request.getSession(true)와 동일
		
		if(member != null && password.equals(member.getPassword())) {
			//로그인 성공
			//request.setAttribute("msg", "로그인에 성공하셨습니다");
			session.setAttribute("msg", "로그인에 성공하셨습니다");
			
			//로그인한 사용자 정보(session이용)
			System.out.println(session.getId()); //클라이언트와 공유하는 JSESSIONID
			session.setAttribute("loginMember", member);
			
			//session timeout : web.xml보다 우선순위 높음
			//초단위로 작성
			//session.setMaxInactiveInterval(30); //로그인성공후 30초동안만 세션유지 -> 30초후 새로고침하면 로그인이 풀린다 
			
			
			//아이디저장
			//saveId : cookie처리		
			Cookie c = new Cookie("saveId",memberId);
			c.setPath(request.getContextPath()); //path : 쿠키를 전송할 url
			if(saveId != null) {
				//아이디저장 체크시
				//기간정하기 (기간을 안정하면 세션쿠키 : 세션이 유효한기간만큼만 유지)
				c.setMaxAge(60 * 60 * 24 * 7); //7일짜리 영속쿠키로 지정
			}else {
				//아이디저장 체크해제시 -> 쿠키를 제거해줘야한다(MaxAge값을 제어)
				c.setMaxAge(0); //0으로 지정해서 즉시 삭제. 음수로 지정하면 세션종료시 제거된다 				
			}
			response.addCookie(c);
			
			
			
		}
		else {
			//로그인 실패
			//request.setAttribute("msg", "로그인에 실패하셨습니다");
			session.setAttribute("msg", "로그인에 실패하셨습니다");
			
			//request.setAttribute("loc", request.getContextPath());
			//4. 응답메세지 html
//			RequestDispatcher reqDispatcher = request.getRequestDispatcher("/index.jsp");
//			reqDispatcher.forward(request, response); //jsp한테 처리위임			
		}
		
		//리다이렉트 : url변경
		response.sendRedirect(request.getContextPath()); //해당주소로 이동
		
	}

}
