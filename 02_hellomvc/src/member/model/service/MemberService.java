package member.model.service;

import java.sql.Connection;
import static common.JDBCTemplate.*;

import member.model.dao.MemberDao;
import member.model.vo.Member;

public class MemberService {
	
	
	private MemberDao memberDao = new MemberDao();
	//회원가입시 이 상수를 가져와서 사용할것
	public static final String MEMBER_ROLE = "U";
	public static final String ADMIN_ROLE = "A";
	
	
	public Member selectOne(String memberId){
		Connection conn = getConnection();
		Member m = memberDao.selectOne(conn, memberId);
		close(conn);
		return m;
	}
	
	
	/**
	 * 
	 * 회원추가
	 */
	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn, member);
		if(result > 0) 
			commit(conn);
		else 
			rollback(conn);
		close(conn);
		return result;
	}
	
	/**
	 * 회원삭제
	 */
	public int deleteMember(String memberId) {
		Connection conn = getConnection();
		int result = memberDao.deleteMember(conn, memberId);
		if(result > 0) 
			commit(conn);
		else 
			rollback(conn);
		close(conn);
		return result;
	}
	
	/**
	 * 회원정보수정
	 */
	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = memberDao.updateMember(conn, m);
		if(result > 0) 
			commit(conn);
		else 
			rollback(conn);
		close(conn);
		return result;
	}
	

}
