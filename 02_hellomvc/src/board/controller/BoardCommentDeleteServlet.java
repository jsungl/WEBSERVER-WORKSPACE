package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;

/**
 * Servlet implementation class BoardCommentDeleteServlet
 */
@WebServlet("/board/boardCommentDelete")
public class BoardCommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int no = Integer.parseInt(request.getParameter("no"));
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));
			System.out.println("no@BoardCommentDeleteServlet = " + no);
			
			int result = boardService.deleteBoardComment(no);
			String msg = result > 0 ? "댓글삭제성공" : "댓글삭제실패";
			
			request.getSession().setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/board/boardView?no=" + boardNo);
		
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

}
