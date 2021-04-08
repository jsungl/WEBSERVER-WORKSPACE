package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MvcFileRenamePolicy;

/**
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet("/board/boardUpdate")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자입력값 
		int no = Integer.parseInt(request.getParameter("no"));
		
		//2. 업무로직
		Board board = boardService.selectBoard(no);
		
		request.setAttribute("board", board);
		request.getRequestDispatcher("/WEB-INF/views/board/boardUpdateForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			//1. MultipartRequest 객체생성
			// /webContent/upload/board/업로드파일명.jpg
			String saveDirectory = getServletContext().getRealPath("/upload/board");
			System.out.println("saveDirectory@servlet = " + saveDirectory);
			
			//최대파일허용크기 10mb = 10 * 1kb * 1kb
			int maxPostSize = 10 * 1024 * 1024;
			
			//인코딩
			String encoding = "utf-8";
			
			//파일명 변경정책 객체
			//중복파일인 경우 numbering 처리(새폴더,새폴더(1),새폴더(2)...)
			//filerename : 20210406191919_123.jpg
			//FileRenamePolicy policy = new DefaultFileRenamePolicy();
			FileRenamePolicy policy = new MvcFileRenamePolicy();
			
			
			
			MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
			
			
			//2. db에 게시글/첨부파일 정보 저장	
			//2-1. 사용자 입력값
			int no = Integer.parseInt(multipartRequest.getParameter("no"));
			String title = multipartRequest.getParameter("title");
			String writer = multipartRequest.getParameter("writer");
			String content = multipartRequest.getParameter("content");
			//System.out.println("title@BoardEnrollServlet = " + title);
			//System.out.println("writer@BoardEnrollServlet = " + writer);
			//System.out.println("content@BoardEnrollServlet = " + content);
			
			//업로드한 파일명
			String originalFileName = multipartRequest.getOriginalFileName("upFile");
			String renamedFileName = multipartRequest.getFilesystemName("upFile");
			
			//삭제할 첨부파일번호
			String attachNo = multipartRequest.getParameter("delFile");
			System.out.println("attachNo@BoardUpdateServlet = " + attachNo);

			
			Board board = new Board();
			board.setNo(no);
			board.setTitle(title);
			board.setWriter(writer);
			board.setContent(content);
			
			//첨부파일이 있는경우
			//multipartRequest.getFile("upFile"):File != null
			if(originalFileName != null) {
				Attachment attach = new Attachment();
				attach.setBoardNo(no);
				attach.setOriginalFileName(originalFileName);
				attach.setRenamedFileName(renamedFileName);
				board.setAttach(attach);
			}
			
			//2. 업무로직 : 
			//첨부파일
			int result = 0;
			if(attachNo != null) {
				result = boardService.deleteAttachment(attachNo);
			}
			
			//db에 update
			result = boardService.updateBoard(board);
			
			//System.out.println("result@BoardEnrollServlet = " + result);
			//3. DML요청 : 리다이렉트 & 사용자피드백(alert)
			//  /mvc/board/boardList
			HttpSession session = request.getSession();
			String msg = (result > 0) ? "게시글 수정 성공" : "게시글 수정 실패";
			String location = request.getContextPath() + "/board/boardView?no=" + board.getNo(); //다시 상세페이지로 이동
			
			
			session.setAttribute("msg", msg);
	
			//리다이렉트
			response.sendRedirect(location);
		
		} catch(Exception e) {
			e.printStackTrace();
			//관리자이메일 알림
			throw e;
		}
	}

}
