package admin.controller;

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
 * Servlet implementation class AdminMemberRoleUpdateServlet
 */
@WebServlet("/admin/memberRoleUpdate")
public class AdminMemberRoleUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값 처리
		String memberId = request.getParameter("memberId");
		String memberRole = request.getParameter("memberRole");
		System.out.println("memberId@servlet = " + memberId);
		System.out.println("memberRole@servlet = " + memberRole);
		
		//2. 업무로직
		//Member member = memberService.selectOne(memberId);
		Member member = new Member();
		member.setMemberRole(memberRole);
		member.setMemberId(memberId);
		
		int result = memberService.updateMemberRole(member);
		
		
		//3. 리다이렉트(/mvc/admin/memberList) 및 사용자 피드백
		HttpSession session = request.getSession();
		if(result > 0) {
			session.setAttribute("msg", "사용자 권한 변경에 성공했습니다.");
			
		}else {
			session.setAttribute("msg", "사용자 권한 변경에 실패했습니다.");
		}
		
		response.sendRedirect(request.getContextPath() + "/admin/memberList");
	}

}
