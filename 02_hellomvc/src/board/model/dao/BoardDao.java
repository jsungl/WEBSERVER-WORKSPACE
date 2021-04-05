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

import board.model.vo.Attachment;
import board.model.vo.Board;


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
	 * 전체 게시글 수
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
	 * 전체 게시글 조회 paging
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
				int no = rset.getInt("no"); 
				String title = rset.getString("title");
				String writer = rset.getString("writer");
				String content = rset.getString("content");
				Date regDate = rset.getDate("reg_date");
				int readCount = rset.getInt("read_count");
				//Attachment attach = 
				
				Board board = new Board(no,title,writer,content,regDate,readCount,null);
				list.add(board);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	/**
	 * 게시글 추가
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
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
}
