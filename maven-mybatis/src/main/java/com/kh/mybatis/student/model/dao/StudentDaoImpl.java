package com.kh.mybatis.student.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.student.model.vo.Student;

public class StudentDaoImpl implements StudentDao {

	@Override
	public int insertStudent(SqlSession session, Student student) {
		// statement : namespace.queryTagId
		return session.insert("student.insertStudent", student); //하나로만든다음 전달해야함(student에 name,tel...포함)
	}

	@Override
	public int insertStudentMap(SqlSession session, Map<String, Object> student) {
		return session.insert("student.insertStudentMap",student); //insertStudentMap : student-mapper.xml의 insert id값
	}

	@Override
	public int selectStudentCount(SqlSession session) {
		return session.selectOne("student.selectStudentCount");
	}

	@Override
	public Student selectOneStudent(SqlSession session, int no) {
		return session.selectOne("student.selectOneStudent", no);
	}

	/**
	 * Map<String, Object>
	 *  - 컬럼명:String
	 * 	- 컬럼값:Object
	 */
	@Override
	public Map<String, Object> selectOneStudentMap(SqlSession session, int no) {
		return session.selectOne("student.selectOneStudentMap", no);
	}

	@Override
	public int updateStudent(SqlSession session, Student student) {
		return session.update("student.updateStudent", student);
	}

	@Override
	public int deleteStudent(SqlSession session, int no) {
		return session.delete("student.deleteStudent", no);
	}
	
	/**
	 * 조회된 행이 없는경우, 빈 ArrayList객체가 리턴된다.(null을 리턴하는 selectOne과 차이점)
	 */
	
	@Override
	public List<Student> selectStudentList(SqlSession session) {
		return session.selectList("student.selectStudentList"); //여러행 조회
	}

	@Override
	public List<Map<String, Object>> selectStudentMapList(SqlSession session) {
		return session.selectList("student.selectStudentMapList");
	}

}
