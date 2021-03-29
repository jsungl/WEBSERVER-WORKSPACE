package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MemberLogoutServlet
 */
@WebServlet("/member/logout")
public class MemberLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//세션무효화 : 세션에 저장된 속성값 모두 폐기
		//만약 세션이 존재하지않으면 새로만들지않고 null을 리턴
		HttpSession session = request.getSession(false); //세션값 가져오기
		System.out.println("session@LogoutServlet = " + session);
		
		//무효화
		if(session != null)
			session.invalidate(); 
		
		//리다이렉트
		response.sendRedirect(request.getContextPath()); //JSESSIONID가 바뀐다 -> 다시 세션id발급받는다
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response); //get호출 -> get으로 오건 post로 오건 동일하게 처리한다는 말
	}

}
