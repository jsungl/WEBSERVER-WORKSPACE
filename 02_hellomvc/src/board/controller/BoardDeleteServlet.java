package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.service.BoardService;

/**
 * Servlet implementation class BoardDeleteServlet
 */
@WebServlet("/board/boardDelete")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println("no@BoardDelServlet = " + no);
			
			//int result = memberService.deleteMember(memberId);
			int result = boardService.deleteBoard(no);
			
			String msg = (result > 0) ? "게시글 삭제 성공" : "게시글 삭제 실패";
			HttpSession session = request.getSession();
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/board/boardList");
		
		} catch(Exception e) {
			//예외로깅
			e.printStackTrace();
			//notify to admin(by email...)
			
			//예외페이지 전환을 위해서 was에 예외던짐
			throw e; //dml,dql다 이렇게 처리
		}
	}

}
