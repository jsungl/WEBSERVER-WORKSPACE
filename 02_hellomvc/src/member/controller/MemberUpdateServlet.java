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
		
		request.setCharacterEncoding("utf-8");
		
		//2. 사용자입력값처리
		String memberId = request.getParameter("memberId");
		String password = request.getParameter("password");
		String memberName = request.getParameter("memberName");
//		String memberRole = request.getParameter("memberRole");
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
				
		
//		System.out.println("memberId@UpdateServlet = " + memberId);
//		System.out.println("password@UpdateServlet = " + password);
//		System.out.println("memberName@UpdateServlet = " + memberName);
//		System.out.println("birthday@UpdateServlet = " + bday);
//		System.out.println("email@UpdateServlet = " + email);
//		System.out.println("phone@UpdateServlet = " + phone);
//		System.out.println("address@UpdateServlet = " + address);
//		System.out.println("gender@UpdateServlet = " + gender);
//		System.out.println("hobby@UpdateServlet = " + hobbies);  
//		System.out.println("memberRole@Servlet = " + memberRole);
		
		
		Member member = new Member(memberId,password,memberName,null,gender,bday,email,phone,address,hobbies,null);
		
		int result = memberService.updateMember(member);
		//System.out.println("result@UpdateServlet = " + result);
		
		//정보수정 성공/실패 여부 판단
		HttpSession session = request.getSession();
		
		
		if(result > 0) {
			//정보수정 성공
			session.setAttribute("msg", "회원정보 수정에 성공하셨습니다");
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
