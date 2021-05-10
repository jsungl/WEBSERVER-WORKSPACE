package com.kh.mybatis.student.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.exception.NoMatchingStudentException;
import com.kh.mybatis.student.model.service.StudentService;
import com.kh.mybatis.student.model.service.StudentServiceImpl;
import com.kh.mybatis.student.model.vo.Student;

public class DeleteStudentController extends AbstractController {

	private StudentService studentService = new StudentServiceImpl();

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost@DeleteStudentController");
		try {
			
			//1. 사용자입력값 처리
			int no = Integer.parseInt(request.getParameter("no"));
			//Student student = new Student();			
			//student.setNo(no);
			//System.out.println("student@DeleteStudentController = " + student);
			
			//2. 업무로직
			int result = studentService.deleteStudent(no);
			if(result == 0) {
				throw new NoMatchingStudentException(String.valueOf(no));
			}
			
			//3. 사용자피드백 및 리다이렉트
			HttpSession session = request.getSession();
			session.setAttribute("msg", "학생정보 삭제 성공!");
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return "redirect:/student/selectOne.do";
	}
	
	
	
	
	
	
}
