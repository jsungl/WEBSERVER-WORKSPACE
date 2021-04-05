package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import common.MvcUtils;

/**
 * 페이징
 * - 한페이지당 게시글수 5개  (numPerPage = 5)
 */
@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//0. 인코딩처리 -> EncodingFilter
		//1. 사용자 입력값
		final int numPerPage = 5;
		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			//처리코드없음. exception발생시 기본값 1 유지
			
		}
		
		//2. 업무로직
		//a. contents영역 : start ~ end
		//cPage = 1 : 1 ~ 5
		//cPage = 2 : 6 ~ 10
		//cPage = 3 : 11 ~ 15
		int start = (cPage - 1) * numPerPage + 1; //1 6 11
		int end = cPage * numPerPage; // 5 10 15
		List<Board> list = boardService.selectAll(start,end);
//		for(Board b : list) {
//			System.out.println("list@servlet = " + b);
//		}
		
		
		//b. pageBar영역
		int totalContents = boardService.selectBoardCount(); //전체 게시글수
		String url = request.getRequestURI(); // url :  /mvc/board/boardList
		String pageBar = MvcUtils.getPageBar(cPage,numPerPage,totalContents,url);
		
		
		//3. 응답 html처리 jsp에 위임
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/board/boardList.jsp").forward(request, response);
		
		
	}

}
