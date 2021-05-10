package com.kh.mybatis.emp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.emp.model.service.EmpService;
import com.kh.mybatis.emp.model.service.EmpServiceImpl;

public class EmpSearchController1 extends AbstractController {

	private EmpService empService = new EmpServiceImpl();

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자입력값
		
		
		
		//2. 업무로직
		List<Map<String, Object>> list = empService.selectAllEmp();
		System.out.println("list@EmpSearchController1 = " + list);
		
		
		//3. jsp위임
		request.setAttribute("list", list);
		
		
		
		
		
		
		
		
		return "emp/search1";
	}
	
	
	
}
