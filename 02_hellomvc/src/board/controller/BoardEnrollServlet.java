package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardEnrollServlet
 */
@WebServlet("/board/boardEnroll")
public class BoardEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		//System.out.println("title@BoardEnrollServlet = " + title);
		//System.out.println("writer@BoardEnrollServlet = " + writer);
		//System.out.println("content@BoardEnrollServlet = " + content);
		
		//2. 업무로직 : db에 insert
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		//System.out.println("board@BoardEnrollServlet = " + board);
		int result = boardService.insertBoard(board);
		//System.out.println("result@BoardEnrollServlet = " + result);
		
		//3. DML요청 : 리다이렉트 & 사용자피드백(alert)
		//  /mvc/board/boardList
		HttpSession session = request.getSession();
		//String msg = (result > 0) ? "게시글 등록 성공" : "게시글 등록 실패";
		if(result > 0) {
			session.setAttribute("msg", "게시글 등록에 성공하셨습니다");
		}else {
			session.setAttribute("msg", "게시글 등록에 실패하셨습니다");
		}

		//리다이렉트
		response.sendRedirect(request.getContextPath() + "/board/boardList");
		
	}

}
