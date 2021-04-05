package admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Paging Recipe
 * A. Contents Section : 쿼리
 * 		1. start rownum ~ end rownum
 * 		2. cpage(현재페이지), numPerPage(페이지당 표시할 컨텐츠수)
 * 
 * B. Pagebar Section : html 작성
 * 		1. totalContents : 총 컨텐츠 수
 * 		2. totalPage : 전체페이지수(totalContents와 numPerPage를 통해 구한다)
 * 		3. pageBarSize : 페이지바에 표시할 페이지 개수 ex) < 1 2 3 4 5 >
 * 		4. pageNo : 페이지넘버를  출력할 증감변수
 *		5. pageStart ~ pageEnd : pageNo의 범위 
 *
 */
@WebServlet("/admin/memberList")
public class AdminMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	
	
	//단순조회라 get메소드만 구현
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값 : 현제페이지 cPage
		final int numPerPage = 10;
		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			//처리코드없음. exception발생시 기본값 1 유지
			
		}
		
		//2. 업무로직 : 전체회원조회 - 회원가입일 내림차순으로 조회
		//cPage = 1 : 1 ~ 10
		//cPage = 2 : 11 ~ 20
		//cPage = 3 : 21 ~ 30
		int start = (cPage - 1) * numPerPage + 1;
		int end = cPage * numPerPage;
//		int start = end - (numPerPage - 1);
		List<Member> list = memberService.selectAll(start,end);
		int totalContents = memberService.selectMemberCounts(); //전체회원수 구하기
		System.out.println("totalContents = " + totalContents);

//		List<Member> list = memberService.selectAll();
//		for(Member m : list) {
//			System.out.println("list@servlet = " + m);
//		}
		
		//3. pagebar영역 설정
		String url = request.getRequestURI(); // url :  /mvc/admin/memberList
		String pageBar = MvcUtils.getPageBar(cPage,numPerPage,totalContents,url); //url : 해당페이지를 눌렀을때 이동할링크
		
		
		
		
		//4. jsp에 html응답메세지 작성 위임
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp").forward(request, response);
		
		
		
		
		
	}

}
