package member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/member/memberUpdate")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberService memberService = new MemberService();
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("utf-8");
		
		//2. 사용자입력값처리
		String memberId = request.getParameter("memberId");
//		String password = request.getParameter("password");
		String memberName = request.getParameter("memberName");
		String birthday = request.getParameter("birthday");
		Date bday = null;
		if(birthday != null && !"".equals(birthday))
			bday = Date.valueOf(birthday);
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		String[] hobby = request.getParameterValues("hobby");
		String hobbies = "";
		if(hobby != null)
			hobbies = String.join(",", hobby); 
		
		
		Member member = new Member(memberId,null,memberName,null,gender,bday,email,phone,address,hobbies,null);
		
		int result = memberService.updateMember(member);
		//System.out.println("result@UpdateServlet = " + result);
		
		//정보수정 성공/실패 여부 판단
		HttpSession session = request.getSession();
		
		
		if(result > 0) {
			//정보수정 성공
			session.setAttribute("msg", "회원정보 수정에 성공하셨습니다");
			//세션의 정보도 갱신
			session.setAttribute("loginMember", memberService.selectOne(memberId)); 
		}else {
			//정보수정 실패
			session.setAttribute("msg", "회원정보 수정에 실패하셨습니다");
		}

		//리다이렉트
		//response.sendRedirect(request.getContextPath());
		response.sendRedirect(request.getContextPath() + "/member/memberView");
	}

}
