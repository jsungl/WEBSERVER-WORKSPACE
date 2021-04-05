package board.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import board.model.dao.BoardDao;
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
	 */
	public int insertBoard(Board board) {
		Connection conn = getConnection();
		int result = boardDao.insertBoard(conn, board);
		if(result > 0) 
			commit(conn);
		else 
			rollback(conn);
		close(conn);
		return result;
	}
}
