package admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberFinderServlet
 */
@WebServlet("/admin/memberFinder")
public class AdminMemberFinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값 처리
		String searchType = request.getParameter("searchType");
		String searchKeyword = request.getParameter("searchKeyword");
		//System.out.println("searchType@AdminFinderServlet = " + searchType);
		//System.out.println("searchKeyword@AdminFinderServlet = " + searchKeyword);
		
		Map<String,String> param = new HashMap<>();
		param.put("searchType", searchType);
		param.put("searchKeyword", searchKeyword);
		//System.out.println("param@AdminMemberFinderServlet = " + param);
		
		final int numPerPage = 10;
		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			//처리코드없음. exception발생시 기본값 1 유지
		}
		
		
		
		//2. 업무로직
		//List<Member> list = memberService.searchMember(param); //검색된 리스트
		//System.out.println("list@AdminMemberFinderServlet = " + list);
		
		int start = (cPage - 1) * numPerPage + 1;
		int end = cPage * numPerPage;
		List<Member> list = memberService.searchMember(param,start,end);
		int totalContents = memberService.searchMemberCounts(param); //전체회원수
		
		//3. pagebar영역 설정
		//request.getQueryString() http://127.0.0.1:9090/mvc/admin/memberFinder?searchType=memberId&searchKeyword=a
		//String url = request.getRequestURI(); // url :  /mvc/admin/memberFinder
		String url = request.getRequestURI() + "?searchType=" + searchType + "&searchKeyword=" + searchKeyword ; 
		//System.out.println("MemberFinderUrl = " + url);
		String pageBar = MvcUtils.getPageBar(cPage,numPerPage,totalContents,url);
		
		
		
		
		//4. jsp에 html응답메세지 작성위임
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp").forward(request, response);
		
	}

}
