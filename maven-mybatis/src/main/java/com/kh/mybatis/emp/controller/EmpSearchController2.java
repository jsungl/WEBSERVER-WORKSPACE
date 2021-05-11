package com.kh.mybatis.emp.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.emp.model.service.EmpService;
import com.kh.mybatis.emp.model.service.EmpServiceImpl;

public class EmpSearchController2 extends AbstractController {

	private EmpService empService = new EmpServiceImpl();

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("--------------doGet메소드 실행@EmpSearchController2--------------");
			//1. 사용자입력값
			String searchType = request.getParameter("searchType");
			String searchKeyword = request.getParameter("searchKeyword");
			String gender = request.getParameter("gender");
			int salary = 0;
			try {
				salary = Integer.parseInt(request.getParameter("salary"));
				System.out.println("salary@EmpSearchController2 = " + salary);
			} catch(NumberFormatException e) {
				//처리코드없음
			}
			String salaryCompare = request.getParameter("salaryCompare");
			String hire_date = request.getParameter("hire_date");
			String hiredateCompare = request.getParameter("hiredateCompare");
			
			//hire_date(문자열)이 아닌 Date타입으로 처리
			Date hireDate = null;
			if(hire_date != null && !"".equals(hire_date))
				hireDate = Date.valueOf(hire_date);
			
			
			Map<String,Object> param = new HashMap<>();
			param.put("searchType", searchType);
			param.put("searchKeyword", searchKeyword);
			param.put("gender", gender); //null일수있다.
			param.put("salary", salary); 
			param.put("salaryCompare", salaryCompare); 
//			param.put("hire_date", hire_date); 
			param.put("hireDate", hireDate); 

			param.put("hiredateCompare", hiredateCompare); 
			
			System.out.println("param@EmpSearchController1 = " + param);
			
			//2. 업무로직
			List<Map<String, Object>> list = empService.search2(param);
			System.out.println("list@EmpSearchController1 = " + list);
		
		
			//3. jsp위임
			request.setAttribute("list", list);
		
		
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		
		
		
		return "emp/search2";
	}
	
	
	
}
