package com.kh.mybatis.student.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.StudentService;
import com.kh.mybatis.student.model.service.StudentServiceImpl;

public class InsertStudentMapController extends AbstractController {
	
	private StudentService studentService = new StudentServiceImpl();

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost@InsertStudentMapController");
		
		try {
			//1. 사용자 입력값 
			String name = request.getParameter("name");
			String tel = request.getParameter("tel");
			Map<String, Object> student = new HashMap<>();
			student.put("name", name);
			student.put("tel", tel);
			System.out.println("studentMap@InsertStudentMapController = " + student);
			
			//2. 업무로직
			int result = studentService.insertStudentMap(student);
			
			
			//3. 사용자 피드백 & redirect
			request.getSession().setAttribute("msg", "학생정보등록성공!");
		
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		return "redirect:/student/insertStudent.do";
	}
	
	
}
