package board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import board.model.dao.BoardDao;
import board.model.vo.Attachment;
import board.model.vo.Board;


public class BoardService {

	private BoardDao boardDao = new BoardDao();

	/**
	 * 전체게시글 수
	 */
	public int selectBoardCount() {
		Connection conn = getConnection();
		int totalContents = boardDao.selectBoardCount(conn);
		close(conn);
		return totalContents;
	}

	/**
	 * 전체 게시글 조회 paging
	 */
	public List<Board> selectAll(int start, int end) {
		Connection conn = getConnection();
		List<Board> list = boardDao.selectAll(conn,start,end);
		close(conn);
		return list;
	}
	
	/**
	 * 게시글 추가
	 * 첨부파일이 있는경우, attach객체를 attachment테이블에 등록한다
	 *  - board등록, attachment등록은 하나의 트랜잭션으로 처리되어야한다(성공시 둘다성공, 실패시 둘다실패되야한다)
	 *  
	 */
	public int insertBoard(Board board) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = boardDao.insertBoard(conn, board);
			
			//생성된 board_no를 가져오기
			int boardNo = boardDao.selectLastBoardNo(conn);
			System.out.println("boardNo@service = " + boardNo);
			
			if(board.getAttach() != null) {
				//참조할 boardNo 세팅
				board.getAttach().setBoardNo(boardNo);
				result = boardDao.insertAttachment(conn,board.getAttach());
			}
			commit(conn);
		}catch(Exception e) {
			e.printStackTrace();
			rollback(conn);
			result = 0;
		}finally {
			close(conn);
		}
		
		
		return result;
	}

	/**
	 * 선택한 게시글 조회
	 */
	public Board selectBoard(int no) {
		Connection conn = getConnection();
		Board b = boardDao.selectBoard(conn,no);
		close(conn);
		return b;
	}
	
	/**
	 * 선택한 게시글 첨부파일 조회
	 */
	public Attachment selectAttach(int no) {
		Connection conn = getConnection();
		Attachment attach = boardDao.selectAttach(conn,no);
		close(conn);
		return attach;
	}

	public Board selectBoard(String title, String writer) {
		Connection conn = getConnection();
		Board b = boardDao.selectBoard(conn,title,writer);
		close(conn);
		return b;
	}
	
	
	
}
