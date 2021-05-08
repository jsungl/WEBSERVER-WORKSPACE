package com.kh.mybatis.student.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.StudentService;
import com.kh.mybatis.student.model.service.StudentServiceImpl;
import com.kh.mybatis.student.model.vo.Student;

public class SelectOneController extends AbstractController {

	private StudentService studentService = new StudentServiceImpl();
	//조회라 doGet만
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet@SelectOneController");
		try {
			//1. 사용자 입력값
			int no = 0;
			try {
				no = Integer.parseInt(request.getParameter("no"));
				//no = Integer.valueOf(request.getParameter("no"));
			} catch(NumberFormatException e) {
				//처리할코드없음
			}
						
			//2. 업무로직
			//2-a. 전체학생수 조회
			int total = studentService.selectStudentCount();
			System.out.println("total@SelectOneController = " + total);
			
			if(no != 0) {
				Student student = studentService.selectOneStudent(no);
				System.out.println("student@SelectOneController = " + student);
				request.setAttribute("student", student);
			}
			
			//3. jsp위임
			request.setAttribute("total", total);
		
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		return "student/selectOne";
	}
	
	
}
