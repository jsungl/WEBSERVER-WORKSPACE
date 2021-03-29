package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class CheckIdDuplicateServlet
 */
@WebServlet("/member/checkIdDuplicate")
public class CheckIdDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자입력값 처리
		String memberId = request.getParameter("memberId");
		System.out.println("memberId@CheckIdDuplicateServlet = " + memberId);
		
		
		//2. 업무로직 : 해당id를 db에서 조회
		Member member = new MemberService().selectOne(memberId); //null이 넘어오면 해당 아이디를 쓸수있다
		boolean available = member == null; //null이면 true, null이 아니면 false
		//System.out.println("available@servlet = " + available);
		request.setAttribute("available", available); //request속성에 담아둔다
		
		
		//3. 응답처리 : 사용가능여부를 jsp에서 html로 작성(popup에 나올html)
		RequestDispatcher reqDispatcher = request.getRequestDispatcher("/WEB-INF/views/member/checkIdDuplicate.jsp");
		reqDispatcher.forward(request, response);
		
	}

}
