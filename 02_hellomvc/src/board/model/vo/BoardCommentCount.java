package board.model.vo;

import java.io.Serializable;
import java.sql.Date;

public class BoardCommentCount extends Board implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int boardCommentCount;

	public BoardCommentCount() {
	}

	public BoardCommentCount(int no, String title, String writer, String content, Date regDate, int readCount, Attachment attach,int boardCommentCount) {
		super(no,title,writer,content,regDate,readCount,attach);
		this.boardCommentCount = boardCommentCount;
	}

	
	public int getBoardCommentCount() {
		return boardCommentCount;
	}

	public void setBoardCommentCount(int boardCommentCount) {
		this.boardCommentCount = boardCommentCount;
	}

	@Override
	public String toString() {
		return "BoardCommentCount [boardCommentCount=" + boardCommentCount + ", " + super.toString() +  "]";
	}

	
	

	
	
}
