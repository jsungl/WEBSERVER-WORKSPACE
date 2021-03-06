package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/member/memberDelete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("utf-8");
		
		String memberId = request.getParameter("memberId");
		//System.out.println("memberId@DeleteServlet = " + memberId);
		
		//업무로직
		int result = memberService.deleteMember(memberId);
		//System.out.println("result@DeleteServlet = " + result);
		
		
		//탈퇴 성공/실패 여부 판단
		HttpSession session = request.getSession();
		
		if(result > 0) {
			//탈퇴 성공
			//session.invalidate(); 
			//session.setAttribute("msg", "탈퇴를 성공하셨습니다");
			response.sendRedirect(request.getContextPath() + "/member/logout");
		}else {
			//탈퇴 실패
			session.setAttribute("msg", "탈퇴를 실패하셨습니다");
			response.sendRedirect(request.getContextPath());
		}

		//리다이렉트
		//response.sendRedirect(request.getContextPath());
		
	}

}
