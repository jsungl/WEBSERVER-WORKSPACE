package member.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static common.JDBCTemplate.*;

import member.model.service.MemberService;
import member.model.vo.Member;

public class MemberDao {

	private Properties prop = new Properties();
	
	
	public MemberDao() {
		String fileName = MemberDao.class.getResource("/sql/member/member-query.properties").getPath();
		try {
			prop.load(new FileReader(fileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Member selectOne(Connection conn, String memberId) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = prop.getProperty("selectOne");
		
		try{
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			//미완성 쿼리문 값대입
			pstmt.setString(1, memberId);
			//쿼리문실행
			rset = pstmt.executeQuery();
			
			if(rset.next()){
				member = new Member();
				member.setMemberId(rset.getString("member_id"));
				member.setPassword(rset.getString("password"));
				member.setMemberName(rset.getString("member_name"));
				member.setMemberRole(rset.getString("member_role"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setHobby(rset.getString("hobby"));
				member.setEnrollDate(rset.getDate("enroll_date"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(rset);
			close(pstmt);
		}
		return member;

	}
	
	
	
	/**
	 * 회원추가
	 */
	public int insertMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("insertMember");
		
		try {
			//PreparedStatment객체 생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,m.getMemberId());
			pstmt.setString(2,m.getPassword());
			pstmt.setString(3,m.getMemberName());
			pstmt.setString(4,m.getMemberRole()); //상수 MemberRole,기본값 U
			pstmt.setString(5,m.getGender());
			pstmt.setDate(6,m.getBirthday());
			pstmt.setString(7,m.getEmail());
			pstmt.setString(8,m.getPhone());
			pstmt.setString(9,m.getAddress());
			pstmt.setString(10,m.getHobby());
			
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 회원삭제
	 */
	public int deleteMember(Connection conn, String memberId) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = prop.getProperty("deleteMember");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
	/**
	 * 회원정보 수정
	 */
	public int updateMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getMemberName());
			pstmt.setDate(2, m.getBirthday());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getAddress());
			pstmt.setString(6, m.getGender());
			pstmt.setString(7, m.getHobby());
			pstmt.setString(8, m.getMemberId());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 비밀번호 변경
	 */
	public int updatePassword(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updatePassword");

		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 쿼리문미완성
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMemberId());

			// 쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			// DML은 executeUpdate()
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}


	/**
	 * 회원전체조회
	 */
	public List<Member> selectAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectAll");
		List<Member> list = null;
		
		try {
			//3. PreparedStatement객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(query);
			//3.1 ? 값대입
			
			//4. 실행 : DML(executeUpdate) -> int, DQL(executeQuery) -> ResultSet
			rset = pstmt.executeQuery();
			//4.1 ResultSet -> Java객체 옮겨담기
			list = new ArrayList<>();
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				Member member = new Member(memberId, password, memberName,memberRole, gender, birthday, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//5. 자원반납(생성역순 rset - pstmt)
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	
	/**
	 * 회원전체조회 paging
	 */
	public List<Member> selectAll(Connection conn, int start, int end) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectPagedList");
		List<Member> list = null;
		
		try {
			//3. PreparedStatement객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,start);
			pstmt.setInt(2, end);
			//3.1 ? 값대입
			
			//4. 실행 : DML(executeUpdate) -> int, DQL(executeQuery) -> ResultSet
			rset = pstmt.executeQuery();
			//4.1 ResultSet -> Java객체 옮겨담기
			list = new ArrayList<>();
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				Member member = new Member(memberId, password, memberName,memberRole, gender, birthday, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//5. 자원반납(생성역순 rset - pstmt)
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	


	/**
	 * 회원권한 변경
	 */
	public int updateMemberRole(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updateMemberRole");

		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 쿼리문미완성
			pstmt.setString(1, member.getMemberRole());
			pstmt.setString(2, member.getMemberId());

			// 쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			// DML은 executeUpdate()
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}


	/**
	 * 회원검색
	 */
	public List<Member> searchMember(Connection conn, Map<String, String> param) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> list = null;
		
		String query = prop.getProperty("searchMember");
		switch(param.get("searchType")) {
		case "memberId" : query += " member_id like '%" + param.get("searchKeyword") + "%'";break;
		case "memberName" : query += " member_name like '%" + param.get("searchKeyword") + "%'";break;
		case "gender" : query += " gender = '" + param.get("searchKeyword") + "'";break;
			
		}
		//System.out.println("query@Dao = " + query);
		
		try {
			//3. PreparedStatement객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(query);
			
			//4. 실행 : DML(executeUpdate) -> int, DQL(executeQuery) -> ResultSet
			rset = pstmt.executeQuery();
			//4.1 ResultSet -> Java객체 옮겨담기
			list = new ArrayList<>();
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				Member member = new Member(memberId, password, memberName,memberRole, gender, birthday, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//5. 자원반납(생성역순 rset - pstmt)
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	
	/**
	 * 회원검색 paging
	 */
	public List<Member> searchMember(Connection conn, Map<String, String> param, int start, int end) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> list = null;
		
		String query = prop.getProperty("searchMemberList");
		//query = setQuery(query,param.get("searchType"),param.get("searchKeyword"));
		switch(param.get("searchType")) {
		case "memberId" : query += " member_id like '%" + param.get("searchKeyword") + "%'";break;
		case "memberName" : query += " member_name like '%" + param.get("searchKeyword") + "%'";break;
		case "gender" : query += " gender = '" + param.get("searchKeyword") + "'";break;
			
		}
		query += ") M where rnum between ? and ?";
		
		try {
			//3. PreparedStatement객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			//3.1 ? 값대입
			
			//4. 실행 : DML(executeUpdate) -> int, DQL(executeQuery) -> ResultSet
			rset = pstmt.executeQuery();
			//4.1 ResultSet -> Java객체 옮겨담기
			list = new ArrayList<>();
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				Member member = new Member(memberId, password, memberName,memberRole, gender, birthday, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//5. 자원반납(생성역순 rset - pstmt)
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	


	/**
	 * 전체회원수 조회
	 */
	public int selectMemberCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalContents = 0;
		
		String query = prop.getProperty("selectMemberCount");
		try {
			//3. PreparedStatement객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(query);
			//3.1 ? 값대입
			
			//4. 실행 : DML(executeUpdate) -> int, DQL(executeQuery) -> ResultSet
			rset = pstmt.executeQuery();
			//4.1 ResultSet -> Java객체 옮겨담기
			if(rset.next()) {
				totalContents = rset.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//5. 자원반납(생성역순 rset - pstmt)
			close(rset);
			close(pstmt);
		}
		
		return totalContents;
	}


	/**
	 * 검색된 전체회원수 조회
	 */
	public int searchMemberCount(Connection conn,Map<String, String> param) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalContents = 0;
		
		String query = prop.getProperty("searchMemberCount");
		//query = setQuery(query,param.get("searchType"),param.get("searchKeyword"));
		switch(param.get("searchType")) {
		case "memberId" : query += " member_id like '%" + param.get("searchKeyword") + "%'";break;
		case "memberName" : query += " member_name like '%" + param.get("searchKeyword") + "%'";break;
		case "gender" : query += " gender = '" + param.get("searchKeyword") + "'";break;
			
		}
		try {
			//3. PreparedStatement객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(query);
			//3.1 ? 값대입
			
			//4. 실행 : DML(executeUpdate) -> int, DQL(executeQuery) -> ResultSet
			rset = pstmt.executeQuery();
			//4.1 ResultSet -> Java객체 옮겨담기
			if(rset.next()) {
				totalContents = rset.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//5. 자원반납(생성역순 rset - pstmt)
			close(rset);
			close(pstmt);
		}
		
		return totalContents;
	}


	/**
	 * query중복처리 메소드
	 */
	public String setQuery(String query,String searchType,String searchKeyword) {
		switch(searchType) {
		case "memberId" : query += " member_id like '%" + searchKeyword + "%'";break;
		case "memberName" : query += " member_name like '%" + searchKeyword + "%'";break;
		case "gender" : query += " gender = '" + searchKeyword + "'";break;
		}
		return query;
	}
	
	
}
