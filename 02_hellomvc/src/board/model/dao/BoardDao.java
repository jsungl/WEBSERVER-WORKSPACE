package board.model.dao;

import static common.JDBCTemplate.*;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import board.model.exception.BoardException;
import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.BoardComment;
import board.model.vo.BoardCommentCount;
import member.model.vo.Member;


public class BoardDao {

	private Properties prop = new Properties();
	
	public BoardDao() {
		//board-query.properties의 내용 읽어와서 prop에저장
		//resources/sql/board-query.properties가 아니라
		//WEB-INF/classes/sql/board/board-query.properties에 접근해야함
		String fileName = BoardDao.class.getResource("/sql/board/board-query.properties").getPath();
		try {
			prop.load(new FileReader(fileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 전체 게시물 수
	 */
	public int selectBoardCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalContents = 0;
		
		String query = prop.getProperty("selectBoardCount");
		try {
			pstmt = conn.prepareStatement(query);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				totalContents = rset.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return totalContents;
	}

	/**
	 * 전체 게시물 조회 paging
	 */
	public List<Board> selectAll(Connection conn, int start, int end) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectPagedList");
		List<Board> list = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,start);
			pstmt.setInt(2, end);
			
			rset = pstmt.executeQuery();
			list = new ArrayList<>();
			while(rset.next()) {
				BoardCommentCount board = new BoardCommentCount();
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setWriter(rset.getString("writer"));
				board.setContent(rset.getString("content"));
				board.setRegDate(rset.getDate("reg_date"));
				board.setReadCount(rset.getInt("read_count"));
				board.setBoardCommentCount(rset.getInt("comment_cnt"));
				
				System.out.println("attach_no@BoardDao = " + rset.getInt("attach_no"));
				//첨부파일이 있는 경우
				if(rset.getInt("attach_no") != 0) {
					Attachment attach = new Attachment();
					attach.setNo(rset.getInt("attach_no"));
					attach.setBoardNo(rset.getInt("no"));
					attach.setOriginalFileName(rset.getString("original_filename"));
					attach.setRenamedFileName(rset.getString("renamed_filename"));
					attach.setStatus("Y".equals(rset.getString("status")) ? true : false);
					board.setAttach(attach);
				}
				
				list.add(board);
			}
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new BoardException("게시물 전체 조회 오류",e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	/**
	 * 게시물 추가
	 */
	public int insertBoard(Connection conn, Board board) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,board.getTitle());
			pstmt.setString(2,board.getWriter());
			pstmt.setString(3,board.getContent());
			
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new BoardException("게시물 등록 오류",e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int selectLastBoardNo(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int boardNo = 0;
		
		String query = prop.getProperty("selectLastBoardNo");
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				boardNo = rset.getInt("board_no");
			}
			
		} catch (SQLException e) {
			throw new BoardException("게시물 등록 번호 조회 오류",e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return boardNo;
	}

	/**
	 * 첨부파일 추가
	 */
	public int insertAttachment(Connection conn, Attachment attach) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, attach.getBoardNo());
			pstmt.setString(2, attach.getOriginalFileName());
			pstmt.setString(3, attach.getRenamedFileName());
			
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new BoardException("게시물 첨부파일 등록 오류",e);
			//e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}

	/**
	 * 선택한 게시물 조회
	 */
	public Board selectBoard(Connection conn, int no) {
		
		Board board = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = prop.getProperty("selectBoard");
		
		try{
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			//미완성 쿼리문 값대입
			pstmt.setInt(1, no);
			//쿼리문실행
			rset = pstmt.executeQuery();
			
			if(rset.next()){
				board = new Board();
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setWriter(rset.getString("writer"));
				board.setReadCount(rset.getInt("read_count"));
				board.setContent(rset.getString("content"));
				board.setRegDate(rset.getDate("reg_date"));
				board.setReadCount(rset.getInt("read_count"));
			}
		}catch(Exception e){
			throw new BoardException("게시물 조회 오류",e);
		}finally{
			close(rset);
			close(pstmt);
		}
		return board;
	}

	/**
	 * 선택한 게시물 첨부파일 조회
	 */
	public Attachment selectAttach(Connection conn, int no) {
		Attachment attach = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = prop.getProperty("selectAttach");
		
		try{
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			//쿼리문미완성
			pstmt.setInt(1, no);
			//쿼리문실행
			//완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			rset = pstmt.executeQuery();
			
			if(rset.next()){
				attach = new Attachment();
				attach.setNo(rset.getInt("no"));
				attach.setBoardNo(rset.getInt("board_no"));
				attach.setOriginalFileName(rset.getString("original_filename"));
				attach.setRenamedFileName(rset.getString("renamed_filename"));
				attach.setStatus("Y".equals(rset.getString("status")) ?  true : false);
			}
		}catch(Exception e){
			throw new BoardException("게시물 첨부파일 조회 오류",e);
		}finally{
			close(rset);
			close(pstmt);
		}
		return attach;
	}

	/**
	 * 게시물 삭제
	 */
	public int deleteBoard(Connection conn, int no) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = prop.getProperty("deleteBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new BoardException("게시물 삭제 오류",e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 게시물 수정
	 */
	public int updateBoard(Connection conn, Board board) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updateBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,board.getTitle());
			pstmt.setString(2,board.getContent());
			pstmt.setInt(3,board.getNo());
			
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new BoardException("게시물 수정 오류",e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 첨부파일 삭제
	 */
	public int deleteAttachment(Connection conn, String attachNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("deleteAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,attachNo); //원래 number타입이지만 자동형변환된다
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new BoardException("첨부파일 삭제 오류",e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	/**
	 * 게시물 댓글작성
	 */
	public int insertBoardComment(Connection conn, BoardComment bc) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("insertBoardComment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,bc.getCommentLevel()); 
			pstmt.setString(2,bc.getWriter()); 
			pstmt.setString(3,bc.getContent()); 
			pstmt.setInt(4,bc.getBoardNo()); 
			//pstmt.setInt(5,bc.getCommentRef());
			pstmt.setObject(5, bc.getCommentRef() == 0 ? null : bc.getCommentRef()); //null값인경우 setInt를 할수없으므로 setObject로 변경
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new BoardException("댓글 등록 오류",e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 게시물 댓글 조회
	 */
	public List<BoardComment> selectBoardCommentList(Connection conn, int no) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectBoardCommentList");
		List<BoardComment> commentList = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,no);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				BoardComment bc = new BoardComment();
				bc.setNo(rset.getInt("no"));
				bc.setCommentLevel(rset.getInt("comment_level"));
				bc.setWriter(rset.getString("writer"));
				bc.setContent(rset.getString("content"));
				bc.setBoardNo(rset.getInt("board_no"));
				bc.setCommentRef(rset.getInt("comment_ref"));
				bc.setRegDate(rset.getDate("reg_date"));
				commentList.add(bc);			
			}
				
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new BoardException("게시물 댓글 조회 오류",e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return commentList;
	}

	/**
	 * 게시물 댓글 삭제
	 */
	public int deleteBoardComment(Connection conn, int no) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("deleteBoardComment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,no); 
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new BoardException("댓글 삭제 오류",e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
}
