package member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberEnrollServlet
 */
@WebServlet("/member/memberEnroll")
public class MemberEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberService memberService = new MemberService();   

	/**
	 * 회원가입 페이지
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/member/memberEnroll.jsp").forward(request, response);
		
	}

	/**
	 * 회원가입 처리 - db에 저장(dml)
	 * 
	 * java.sql.Date객체 생성하기 : Date.valueOf("1990-09-09")
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				//1. encoding처리
				//request.setCharacterEncoding("utf-8");
				
				
				//2. 사용자입력값처리
				String memberId = request.getParameter("memberId");
				String password = MvcUtils.getSha512(request.getParameter("password")); //리턴된 hash값(인코딩된값)을 db에 저장
				String memberName = request.getParameter("memberName");
				String birthday = request.getParameter("birthday");
				Date bday = null;
				if(birthday != null && !birthday.equals(""))
					bday = Date.valueOf(birthday);
				//java.sql.Date의 valueOf(String s) 메서드는 입력받 문자열 값을 가지고 날짜(Date)로 변경해 준다. 
				//이 때 주의할 것은 날짜형식이 yyyy-mm-dd로 되어야 한다. valueOf("20101122") 이렇게 yyyymmdd 로 입력할 경우 IllegalArgumentException이 발생한다.
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				String address = request.getParameter("address");
				String gender = request.getParameter("gender");
				String[] hobby = request.getParameterValues("hobby");
				String hobbies = "";
				if(hobby != null)
					hobbies = String.join(",", hobby); //배열을 문자열로 변환(구분자추가)								
				
				
				//3. 업무로직 : memberId로 회원객체를 조회 
				//servlet이 처리해야할 업무로직 ->service에 맡긴다
				Member member = new Member();
				member.setMemberId(memberId);
				member.setPassword(password);
				member.setMemberName(memberName);
				member.setMemberRole(MemberService.MEMBER_ROLE);
				member.setBirthday(bday); //Date
				member.setEmail(email);
				member.setPhone(phone);
				member.setAddress(address);
				member.setGender(gender);
				member.setHobby(hobbies); //String ,로 
				
				System.out.println("입력한 회원정보 : " + member);
			
				
				int result = memberService.insertMember(member);
				System.out.println("result@EnrollServlet = " + result);
		
		
		
				//회원가입 성공/실패 여부 판단
				HttpSession session = request.getSession();
				if(result > 0) {
					//회원가입 성공
					session.setAttribute("msg", "회원가입에 성공하셨습니다");
				}else {
					//회원가입 실패
					session.setAttribute("msg", "회원가입에 실패하셨습니다");
				}
		
				//리다이렉트
				response.sendRedirect(request.getContextPath());
		
		
		
	}

}
